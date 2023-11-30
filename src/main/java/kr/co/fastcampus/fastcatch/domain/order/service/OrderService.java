package kr.co.fastcampus.fastcatch.domain.order.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import kr.co.fastcampus.fastcatch.common.exception.AlreadyOrderCanceledException;
import kr.co.fastcampus.fastcatch.common.exception.AlreadyReservedRoomException;
import kr.co.fastcampus.fastcatch.common.exception.InvalidOrderStatusException;
import kr.co.fastcampus.fastcatch.common.exception.OrderNotFoundException;
import kr.co.fastcampus.fastcatch.common.exception.OrderUnauthorizedException;
import kr.co.fastcampus.fastcatch.common.utility.AvailableOrderUtil;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Room;
import kr.co.fastcampus.fastcatch.domain.accommodation.service.AccommodationService;
import kr.co.fastcampus.fastcatch.domain.cart.service.CartService;
import kr.co.fastcampus.fastcatch.domain.member.entity.Member;
import kr.co.fastcampus.fastcatch.domain.member.service.MemberService;
import kr.co.fastcampus.fastcatch.domain.order.dto.request.OrderByCartRequest;
import kr.co.fastcampus.fastcatch.domain.order.dto.request.OrderItemRequest;
import kr.co.fastcampus.fastcatch.domain.order.dto.request.OrderRequest;
import kr.co.fastcampus.fastcatch.domain.order.dto.response.OrderItemResponse;
import kr.co.fastcampus.fastcatch.domain.order.dto.response.OrderPageResponse;
import kr.co.fastcampus.fastcatch.domain.order.dto.response.OrderResponse;
import kr.co.fastcampus.fastcatch.domain.order.dto.response.OrdersResponse;
import kr.co.fastcampus.fastcatch.domain.order.entity.Order;
import kr.co.fastcampus.fastcatch.domain.order.entity.OrderItem;
import kr.co.fastcampus.fastcatch.domain.order.entity.OrderRecord;
import kr.co.fastcampus.fastcatch.domain.order.entity.OrderStatus;
import kr.co.fastcampus.fastcatch.domain.order.repository.OrderItemRepository;
import kr.co.fastcampus.fastcatch.domain.order.repository.OrderRecordRepository;
import kr.co.fastcampus.fastcatch.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderRecordRepository orderRecordRepository;
    private final AccommodationService accommodationService;
    private final MemberService memberService;
    private final CartService cartService;

    public OrderResponse createOrder(Long memberId, OrderRequest orderRequest) {
        Order order = orderRequest.toEntity(memberService.findMemberById(memberId));
        for (OrderItemRequest orderItemRequest : orderRequest.orderItems()) {
            createOrderItem(orderItemRequest, order);
            checkOrderRecord(orderItemRequest);
        }
        orderRepository.save(order);
        List<OrderItemResponse> orderItemResponses = new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItems()) {
            createOrderRecord(orderItem);
            orderItemResponses.add(OrderItemResponse.from(orderItem));
        }
        return OrderResponse.from(order, orderItemResponses, "reserved");
    }

    public OrderResponse createOrderByCart(Long memberId, OrderByCartRequest orderByCartRequest) {
        Order order = orderByCartRequest.toEntity(memberService.findMemberById(memberId));
        for (Long cartItemId : orderByCartRequest.cartItemIds()) {
            OrderItemRequest orderItemRequest = OrderItemRequest.fromCartItem(
                cartService.findCartItemById(cartItemId)
            );
            createOrderItem(orderItemRequest, order);
            checkOrderRecord(orderItemRequest);
            cartService.deleteCartItem(memberId, cartItemId);
        }
        orderRepository.save(order);
        List<OrderItemResponse> orderItemResponses = new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItems()) {
            createOrderRecord(orderItem);
            orderItemResponses.add(OrderItemResponse.from(orderItem));
        }
        return OrderResponse.from(order, orderItemResponses, "reserved");
    }

    public void createOrderItem(OrderItemRequest orderItemRequest, Order order) {
        checkOrderAvailable(orderItemRequest);
        OrderItem orderItem = OrderItem.builder()
            .startDate(orderItemRequest.startDate()).endDate(orderItemRequest.endDate())
            .headCount(orderItemRequest.headCount())
            .price(orderItemRequest.orderPrice())
            .order(order)
            .room(accommodationService.findRoomById(orderItemRequest.roomId()))
            .build();

        order.getOrderItems().add(orderItem);
    }

    public void createOrderRecord(OrderItem orderItem) {
        for (LocalDate date = orderItem.getStartDate();
            date.isBefore(orderItem.getEndDate()); date = date.plusDays(1)) {
            OrderRecord orderRecord = OrderRecord.builder()
                .accommodation(accommodationService.findRoomById((orderItem.getRoom().getId()))
                    .getAccommodation())
                .room(accommodationService.findRoomById(orderItem.getRoom().getId()))
                .order(orderItem.getOrder())
                .stayDate(date).build();
            orderRecordRepository.save(orderRecord);
        }
    }

    public void checkOrderRecord(OrderItemRequest orderItemRequest) {
        for (LocalDate stayDate = orderItemRequest.startDate();
            stayDate.isBefore(orderItemRequest.endDate()); stayDate = stayDate.plusDays(1)) {
            if (orderRecordRepository.existsByRoomIdAndStayDate(orderItemRequest.roomId(),
                stayDate)) {
                throw new AlreadyReservedRoomException();
            }
        }
    }

    public OrdersResponse findOrders(Long memberId, Pageable pageable) {
        List<OrderPageResponse> orderPageResponses = new ArrayList<>();
        orderPageResponses.add(findOrdersByStatus(memberId, "reserved", pageable));
        orderPageResponses.add(findOrdersByStatus(memberId, "used", pageable));
        orderPageResponses.add(findOrdersByStatus(memberId, "canceled", pageable));
        return OrdersResponse.from(orderPageResponses);
    }

    private boolean isValidOrderStatus(String status) {
        return Arrays.asList("reserved", "used", "canceled").contains(status);
    }

    public OrderPageResponse findOrdersByStatus(Long memberId, String status, Pageable pageable) {
        Member member = memberService.findMemberById(memberId);
        Page<Order> orders = new PageImpl<>(Collections.emptyList());

        if (status.equals("reserved")) {
            orders = orderRepository.findOrdersReserved(member, OrderStatus.COMPLETED,
                LocalDate.now(), pageable);
        } else if (status.equals("used")) {
            orders = orderRepository.findOrdersUsed(member, OrderStatus.COMPLETED, LocalDate.now(),
                pageable);
        } else if (status.equals("canceled")) {
            orders = orderRepository.findByMemberAndOrderStatusOrderByCreatedDateDesc(member,
                OrderStatus.CANCELED, pageable);
        } else {
            if (!isValidOrderStatus(status)) {
                throw new InvalidOrderStatusException();
            }
        }

        Page<OrderResponse> orderResponsePage = orders.map(
            order -> mapToOrderResponse(order, status)
        );
        return OrderPageResponse.from(orderResponsePage, status);
    }

    private OrderResponse mapToOrderResponse(Order order, String status) {
        List<OrderItem> orderItems = orderItemRepository.findByOrderOrderId(order.getOrderId());
        List<OrderItemResponse> orderItemResponses = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            orderItemResponses.add(OrderItemResponse.from(orderItem));
        }
        return OrderResponse.from(order, orderItemResponses, status);
    }

    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
    }

    public void deleteOrder(Long memberId, Long orderId) {
        Order order = findOrderById(orderId);
        if (order.getMember().getMemberId() != memberId) {
            throw new OrderUnauthorizedException();
        }
        if (order.getOrderStatus().equals(OrderStatus.CANCELED)) {
            throw new AlreadyOrderCanceledException();
        }
        order.setOrderCanceled();
        orderRepository.save(order);
        orderRecordRepository.deleteByOrder(order);
    }

    private void checkOrderAvailable(OrderItemRequest orderItemRequest) {
        Room room = accommodationService.findRoomById((orderItemRequest.roomId()));
        AvailableOrderUtil.validateDate(orderItemRequest.startDate(), orderItemRequest.endDate());
        AvailableOrderUtil.validateHeadCount(
            orderItemRequest.headCount(), room.getBaseHeadCount(), room.getMaxHeadCount()
        );
    }
}
