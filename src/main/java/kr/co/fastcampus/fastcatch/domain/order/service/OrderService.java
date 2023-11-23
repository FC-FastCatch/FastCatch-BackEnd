package kr.co.fastcampus.fastcatch.domain.order.service;

import java.time.LocalDate;
import java.util.Optional;
import kr.co.fastcampus.fastcatch.common.utility.DateUtil;
import kr.co.fastcampus.fastcatch.domain.order.dto.OrderItemRequest;
import kr.co.fastcampus.fastcatch.domain.order.dto.OrderRequest;
import kr.co.fastcampus.fastcatch.domain.order.entity.Order;
import kr.co.fastcampus.fastcatch.domain.order.entity.OrderItem;
import kr.co.fastcampus.fastcatch.domain.order.entity.OrderStatus;
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

    public void createOrder(Long memberId, OrderRequest orderRequest) {

        Order order = Order.builder()
            .reservationPersonName(orderRequest.reservationPersonName())
            .reservationPhoneNumber(orderRequest.reservationPhoneNumber())
            .totalPrice(orderRequest.totalPrice())
            .orderStatus(OrderStatus.COMPLETED)
            .build();
        orderRepository.save(order);

        Long orderId = order.getOrderId();

        for (OrderItemRequest orderItemRequest : orderRequest.orderItemRequests()) {
            createOrderItem(orderItemRequest, orderId);
        }
    }

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

    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
    }

    public void updateOrderStatusToCanceled(Long memberId, Long orderId) {
        Order order = getOrder(orderId);
        order.setOrderCanceled();
        orderRepository.save(order);
    }
}
