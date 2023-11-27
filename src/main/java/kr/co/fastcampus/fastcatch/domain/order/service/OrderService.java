package kr.co.fastcampus.fastcatch.domain.order.service;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import kr.co.fastcampus.fastcatch.domain.cartitem.repository.CartItemRepository;
import kr.co.fastcampus.fastcatch.domain.member.entity.Member;
import kr.co.fastcampus.fastcatch.domain.member.repository.MemberRepository;
import kr.co.fastcampus.fastcatch.domain.order.dto.OrderByCartRequest;
import kr.co.fastcampus.fastcatch.domain.order.dto.OrderItemRequest;
import kr.co.fastcampus.fastcatch.domain.order.dto.OrderItemResponse;
import kr.co.fastcampus.fastcatch.domain.order.dto.OrderPageResponse;
import kr.co.fastcampus.fastcatch.domain.order.dto.OrderResponse;
import kr.co.fastcampus.fastcatch.domain.order.dto.OrdersResponse;
import kr.co.fastcampus.fastcatch.domain.order.entity.OrderStatus;
import kr.co.fastcampus.fastcatch.common.exception.InvalidOrderStatusException;
import kr.co.fastcampus.fastcatch.domain.order.repository.OrderRecordRepository;
import kr.co.fastcampus.fastcatch.domain.order.dto.OrderRequest;
import kr.co.fastcampus.fastcatch.domain.order.entity.Order;
import kr.co.fastcampus.fastcatch.domain.order.entity.OrderItem;
import kr.co.fastcampus.fastcatch.domain.order.entity.OrderRecord;
import kr.co.fastcampus.fastcatch.common.exception.OrderNotFoundException;
import kr.co.fastcampus.fastcatch.common.exception.OrderUnauthorizedException;
import kr.co.fastcampus.fastcatch.domain.order.repository.OrderItemRepository;
import kr.co.fastcampus.fastcatch.domain.order.repository.OrderRepository;
import kr.co.fastcampus.fastcatch.domain.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        Long newOrderId = orderRepository.save(
            orderRequest.toEntity(memberRepository.findById(memberId).orElseThrow())).getOrderId();
        for (OrderItemRequest orderItemRequest : orderRequest.orderItemRequests()) {
            createOrderItem(orderItemRequest, newOrderId);
            createOrderRecord(orderItemRequest, newOrderId);
        }
    }

    /***
     * 장바구니를 통한 주문 생성
     * @param memberId 회원 ID
     * @param orderByCartRequest 장바구니를 통한 주문 요청 DTO
     */
    public void createOrderByCart(Long memberId, OrderByCartRequest orderByCartRequest) {
        Long newOrderId = orderRepository.save(
                orderByCartRequest.toEntity(memberRepository.findById(memberId).orElseThrow()))
            .getOrderId();
        for (Long cartItemId : orderByCartRequest.cartItemIds()) {
            OrderItemRequest orderItemRequest = OrderItemRequest.fromCartItem(
                cartItemRepository.findById(cartItemId).orElseThrow());
            createOrderItem(orderItemRequest, newOrderId);
            createOrderRecord(orderItemRequest, newOrderId);
            cartItemRepository.deleteById(cartItemId);
        }
    }

    /***
     * 주문 아이템 생성
     *
     * @param orderItemRequest 주문 아이템 요청 DTO
     * @param orderId 주문 ID
     */
    public void createOrderItem(OrderItemRequest orderItemRequest, Long orderId) {
        OrderItem orderItem = OrderItem.builder()
            .startDate(orderItemRequest.startDate()).endDate(orderItemRequest.endDate())
            .headCount(orderItemRequest.headCount())
            .price(orderItemRequest.orderPrice())
            .order(findOrderById(orderId))
            .build();
        findOrderById(orderId).getOrderItems().add(orderItem);
    }

    /***
     * 주문 현황 생성
     *
     * @param orderItemRequest 주문 아이템 요청 DTO
     */
    public void createOrderRecord(OrderItemRequest orderItemRequest, Long orderId) {
        for (LocalDate date = orderItemRequest.startDate();
            date.isBefore(orderItemRequest.endDate()); date = date.plusDays(1)) {
            //accommodation 추가 예정
            OrderRecord orderRecord = OrderRecord.builder()
                .room(roomRepository.findById(orderItemRequest.roomId()).orElseThrow())
                .order(findOrderById(orderId))
                .stayDate(date).build();
            orderRecordRepository.save(orderRecord);
        }
    }

    /***
     * 주문 목록 조회
     * @param memberId 회원 ID
     * @param pageable 페이징 정보
     * @return 주문 목록 응답 DTO
     */
    public OrdersResponse findOrders(Long memberId, Pageable pageable) {
        List<OrderPageResponse> orderPageResponses = new ArrayList<>();
        orderPageResponses.add(findOrdersByStatus(memberId, "RESERVED", pageable));
        orderPageResponses.add(findOrdersByStatus(memberId, "USED", pageable));
        orderPageResponses.add(findOrdersByStatus(memberId, "CANCELED", pageable));
        return OrdersResponse.from(orderPageResponses);
    }

    /***
     * 주문 상태 값 유효성 검증
     * @param status 주문 상태 값
     * @return 유효 여부
     */
    private boolean isValidOrderStatus(String status) {
        return Arrays.asList("RESERVED", "USED", "CANCELED").contains(status);
    }

    /***
     * 특정 주문 상태에 대한 주문 목록 조회
     * @param memberId 회원 ID
     * @param status 주문 상태
     * @param pageable 페이징 정보
     * @return 주문 페이징 응답 DTO
     */
    public OrderPageResponse findOrdersByStatus(Long memberId, String status, Pageable pageable) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        Page<Order> orders = new PageImpl<>(Collections.emptyList());

        if (status.equals("RESERVED")) {
            orders = orderRepository.findOrdersReserved(member, OrderStatus.COMPLETED,
                LocalDate.now(), pageable);
        } else if (status.equals("USED")) {
            orders = orderRepository.findOrdersUsed(member, OrderStatus.COMPLETED, LocalDate.now(),
                pageable);
        } else if (status.equals("CANCELED")) {
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
        return OrderPageResponse.from(orderResponsePage);
    }

    /***
     * 주문 정보 조회
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
}
