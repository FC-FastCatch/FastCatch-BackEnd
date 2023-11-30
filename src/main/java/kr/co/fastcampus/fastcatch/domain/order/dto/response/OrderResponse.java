package kr.co.fastcampus.fastcatch.domain.order.dto.response;

import java.time.LocalDate;
import java.util.List;
import kr.co.fastcampus.fastcatch.domain.order.entity.Order;
import lombok.Builder;

@Builder
public record OrderResponse(
    Long orderId,
    String orderStatus,
    LocalDate orderDate,
    Integer totalPrice,
    String reservationPersonName,
    String reservationPhoneNumber,
    List<OrderItemResponse> orderItems

) {

    public static OrderResponse from(Order order, List<OrderItemResponse> orderItems,
        String status) {
        return OrderResponse.builder().orderId(order.getOrderId()).orderStatus(status)
            .orderDate(order.getOrderDate()).totalPrice(order.getTotalPrice())
            .reservationPersonName(order.getReservationPersonName()).reservationPhoneNumber(
                order.getReservationPhoneNumber()).orderItems(orderItems).build();
    }
}