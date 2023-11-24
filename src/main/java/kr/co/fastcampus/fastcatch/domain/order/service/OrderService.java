package kr.co.fastcampus.fastcatch.domain.order.service;

import java.time.LocalDate;
import java.util.Optional;
import kr.co.fastcampus.fastcatch.common.utility.DateUtil;
import kr.co.fastcampus.fastcatch.domain.order.dto.OrderItemRequest;
import kr.co.fastcampus.fastcatch.domain.order.dto.OrderRequest;
import kr.co.fastcampus.fastcatch.domain.order.entity.Order;
import kr.co.fastcampus.fastcatch.domain.order.entity.OrderItem;
import kr.co.fastcampus.fastcatch.domain.order.exception.OrderNotFoundException;
import kr.co.fastcampus.fastcatch.domain.order.repository.OrderItemRepository;
import kr.co.fastcampus.fastcatch.domain.order.repository.OrderRepository;
import kr.co.fastcampus.fastcatch.domain.room.entity.Room;
import kr.co.fastcampus.fastcatch.domain.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final RoomRepository roomRepository;

    /***
     * 주문 생성
     *
     * @param memberId 회원 ID
     * @param orderRequest 주문 요청 DTO
     */
    public void createOrder(Long memberId, OrderRequest orderRequest) {

        Order newOrder = orderRepository.save(orderRequest.toEntity());
        for (OrderItemRequest orderItemRequest : orderRequest.orderItemRequests()) {
            createOrderItem(orderItemRequest, newOrder.getOrderId());
        }
    }

    /***
     * 주문 아이템 생성
     *
     * @param orderItemRequest 주문 아이템 요청 DTO
     * @param orderId 주문 ID
     */
    public void createOrderItem(OrderItemRequest orderItemRequest, Long orderId) {

        LocalDate checkInDate = DateUtil.stringToDate(orderItemRequest.startDate());
        LocalDate checkOutDate = DateUtil.stringToDate(orderItemRequest.endDate());
        Optional<Room> room = roomRepository.findById(orderItemRequest.roomId());
        OrderItem orderItem = OrderItem.builder()
            .startDate(checkInDate).endDate(checkOutDate)
            .headCount(orderItemRequest.headCount())
            .price(orderItemRequest.orderPrice())
            .order(getOrder(orderId))
            .room(room.orElseThrow())
            .build();
        orderItemRepository.save(orderItem);
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
     * 주문 취소로 주문 상태 변경
     *
     * @param memberId 회원 ID
     * @param orderId 주문 ID
     */
    public void updateOrderStatusToCanceled(Long memberId, Long orderId) {
        Order order = getOrder(orderId);
        order.setOrderCanceled();
        orderRepository.save(order);
    }
}
