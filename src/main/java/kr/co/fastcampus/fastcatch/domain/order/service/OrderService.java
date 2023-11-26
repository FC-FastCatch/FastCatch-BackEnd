package kr.co.fastcampus.fastcatch.domain.order.service;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import kr.co.fastcampus.fastcatch.domain.cartitem.repository.CartItemRepository;
import kr.co.fastcampus.fastcatch.domain.member.repository.MemberRepository;
import kr.co.fastcampus.fastcatch.domain.order.dto.OrderByCartRequest;
import kr.co.fastcampus.fastcatch.domain.order.dto.OrderItemRequest;
import kr.co.fastcampus.fastcatch.domain.order.repository.OrderRecordRepository;
import kr.co.fastcampus.fastcatch.domain.order.dto.OrderRequest;
import kr.co.fastcampus.fastcatch.domain.order.entity.Order;
import kr.co.fastcampus.fastcatch.domain.order.entity.OrderItem;
import kr.co.fastcampus.fastcatch.domain.order.entity.OrderRecord;
import kr.co.fastcampus.fastcatch.domain.order.exception.OrderNotFoundException;
import kr.co.fastcampus.fastcatch.domain.order.exception.OrderUnauthorizedException;
import kr.co.fastcampus.fastcatch.domain.order.repository.OrderItemRepository;
import kr.co.fastcampus.fastcatch.domain.order.repository.OrderRepository;
import kr.co.fastcampus.fastcatch.domain.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
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
            .order(getOrder(orderId))
            .build();
        orderItemRepository.save(orderItem);
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
                .order(getOrder(orderId))
                .stayDate(date).build();
            orderRecordRepository.save(orderRecord);
        }
    }

    /***
     * 주문 Entity 조회
     *
     * @param orderId 주문 ID
     * @return 주문 Entity
     */
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
    }

    /***
     * 주문 취소
     * @param memberId 회원 ID
     * @param orderId 주문 ID
     */
    public void deleteOrder(Long memberId, Long orderId) {
        Order order = getOrder(orderId);
        if (order.getMember().getMemberId() != memberId) {
            throw new OrderUnauthorizedException();
        }
        order.setOrderCanceled();
        orderRepository.save(order);
        orderRecordRepository.deleteByOrder(order);
    }
}
