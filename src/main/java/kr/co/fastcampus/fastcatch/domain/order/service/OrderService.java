package kr.co.fastcampus.fastcatch.domain.order.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import kr.co.fastcampus.fastcatch.common.exception.MemberNotFoundException;
import kr.co.fastcampus.fastcatch.common.exception.RoomNotFoundException;
import kr.co.fastcampus.fastcatch.domain.accommodation.repository.RoomRepository;
import kr.co.fastcampus.fastcatch.domain.cart.repository.CartItemRepository;
import kr.co.fastcampus.fastcatch.domain.member.entity.Member;
import kr.co.fastcampus.fastcatch.domain.member.repository.MemberRepository;
import kr.co.fastcampus.fastcatch.domain.order.dto.request.OrderByCartRequest;
import kr.co.fastcampus.fastcatch.domain.order.dto.request.OrderItemRequest;
import kr.co.fastcampus.fastcatch.domain.order.dto.response.OrderItemResponse;
import kr.co.fastcampus.fastcatch.domain.order.dto.response.OrderPageResponse;
import kr.co.fastcampus.fastcatch.domain.order.dto.response.OrderResponse;
import kr.co.fastcampus.fastcatch.domain.order.dto.response.OrdersResponse;
import kr.co.fastcampus.fastcatch.domain.order.entity.OrderStatus;
import kr.co.fastcampus.fastcatch.common.exception.InvalidOrderStatusException;
import kr.co.fastcampus.fastcatch.domain.order.repository.OrderRecordRepository;
import kr.co.fastcampus.fastcatch.domain.order.dto.request.OrderRequest;
import kr.co.fastcampus.fastcatch.domain.order.entity.Order;
import kr.co.fastcampus.fastcatch.domain.order.entity.OrderItem;
import kr.co.fastcampus.fastcatch.domain.order.entity.OrderRecord;
import kr.co.fastcampus.fastcatch.common.exception.OrderNotFoundException;
import kr.co.fastcampus.fastcatch.common.exception.OrderUnauthorizedException;
import kr.co.fastcampus.fastcatch.domain.order.repository.OrderItemRepository;
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
    //추후 각 도메인 service method 활용하여 수정 예정
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;
    private final CartItemRepository cartItemRepository;

    /***
     * 주문 생성
     *
     * @param memberId 회원 ID
     * @param orderRequest 주문 요청 DTO
     */
    public void createOrder(Long memberId, OrderRequest orderRequest) {
        Order order = orderRequest.toEntity(
            memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new));
        for (OrderItemRequest orderItemRequest : orderRequest.orderItems()) {
            createOrderItem(orderItemRequest, order);
            createOrderRecord(orderItemRequest, order);
        }
        orderRepository.save(order);
    }

    /***
     * 장바구니를 통한 주문 생성
     *
     * @param memberId 회원 ID
     * @param orderByCartRequest 장바구니를 통한 주문 요청 DTO
     */
    public void createOrderByCart(Long memberId, OrderByCartRequest orderByCartRequest) {
        Order order = orderByCartRequest.toEntity(
            memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new));
        for (Long cartItemId : orderByCartRequest.cartItemIds()) {
            OrderItemRequest orderItemRequest = OrderItemRequest.fromCartItem(
                cartItemRepository.findById(cartItemId).orElseThrow());
            createOrderItem(orderItemRequest, order);
            createOrderRecord(orderItemRequest, order);
            cartItemRepository.deleteById(cartItemId);
        }
        orderRepository.save(order);
    }

    /***
     * 주문 아이템 생성
     *
     * @param orderItemRequest 주문 아이템 요청 DTO
     * @param order 주문 Entity
     */
    public void createOrderItem(OrderItemRequest orderItemRequest, Order order) {
        OrderItem orderItem = OrderItem.builder()
            .startDate(orderItemRequest.startDate()).endDate(orderItemRequest.endDate())
            .headCount(orderItemRequest.headCount())
            .price(orderItemRequest.orderPrice())
            .order(order)
            .room(roomRepository.findById(orderItemRequest.roomId())
                .orElseThrow(RoomNotFoundException::new))
            .build();

        order.getOrderItems().add(orderItem);
    }

    /***
     * 주문 현황 생성
     *
     * @param orderItemRequest 주문 아이템 요청 DTO
     * @param order 주문 Entity
     */
    public void createOrderRecord(OrderItemRequest orderItemRequest, Order order) {
        for (LocalDate date = orderItemRequest.startDate();
            date.isBefore(orderItemRequest.endDate()); date = date.plusDays(1)) {
            OrderRecord orderRecord = OrderRecord.builder()
                .accommodation(roomRepository.findById(orderItemRequest.roomId()).orElseThrow(
                        RoomNotFoundException::new)
                    .getAccommodation())
                .room(roomRepository.findById(orderItemRequest.roomId())
                    .orElseThrow(RoomNotFoundException::new))
                .order(order)
                .stayDate(date).build();
            orderRecordRepository.save(orderRecord);
        }
    }

    /***
     * 주문 목록 조회
     *
     * @param memberId 회원 ID
     * @param pageable 페이징 정보
     * @return 주문 목록 응답 DTO
     */
    public OrdersResponse findOrders(Long memberId, Pageable pageable) {
        List<OrderPageResponse> orderPageResponses = new ArrayList<>();
        orderPageResponses.add(findOrdersByStatus(memberId, "reserved", pageable));
        orderPageResponses.add(findOrdersByStatus(memberId, "used", pageable));
        orderPageResponses.add(findOrdersByStatus(memberId, "canceled", pageable));
        return OrdersResponse.from(orderPageResponses);
    }

    /***
     * 주문 상태 값 유효성 검증
     *
     * @param status 주문 상태 값
     * @return 유효 여부
     */
    private boolean isValidOrderStatus(String status) {
        return Arrays.asList("reserved", "used", "canceled").contains(status);
    }

    /***
     * 특정 주문 상태에 대한 주문 목록 조회
     *
     * @param memberId 회원 ID
     * @param status 주문 상태
     * @param pageable 페이징 정보
     * @return 주문 페이징 응답 DTO
     */
    public OrderPageResponse findOrdersByStatus(Long memberId, String status, Pageable pageable) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);
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

    /***
     * 주문 정보 조회
     *
     * @param order 주문
     * @param status 주문 상태
     * @return 주문 정보 응답 DTO
     */
    private OrderResponse mapToOrderResponse(Order order, String status) {
        List<OrderItem> orderItems = orderItemRepository.findByOrder_OrderId(order.getOrderId());
        List<OrderItemResponse> orderItemResponses = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            orderItemResponses.add(OrderItemResponse.from(orderItem));
        }
        return OrderResponse.from(order, orderItemResponses, status);
    }

    /***
     * 주문 Entity 조회
     *
     * @param orderId 주문 ID
     * @return 주문 Entity
     */
    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
    }

    /***
     * 주문 취소
     *
     * @param memberId 회원 ID
     * @param orderId 주문 ID
     */
    public void deleteOrder(Long memberId, Long orderId) {
        Order order = findOrderById(orderId);
        if (order.getMember().getMemberId() != memberId) {
            throw new OrderUnauthorizedException();
        }
        order.setOrderCanceled();
        orderRepository.save(order);
        orderRecordRepository.deleteByOrder(order);
    }

    public long countAccommodationAtDate(Long accommodationId, LocalDate stayDate) {
        return orderRecordRepository.countByAccommodationIdAndStayDate(
            accommodationId, stayDate
        );
    }

    public boolean existsByRoomIdOnDate(Long roomId, LocalDate stayDate) {
        return orderRecordRepository.existsByRoomIdAndStayDate(
            roomId, stayDate
        );
    }
}
