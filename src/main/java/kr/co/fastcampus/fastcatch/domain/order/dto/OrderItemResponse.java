package kr.co.fastcampus.fastcatch.domain.order.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import kr.co.fastcampus.fastcatch.domain.order.entity.OrderItem;
import lombok.Builder;

@Builder
public record OrderItemResponse(
    Long accommodationId,
    String accommodationName,
    Long roomId,
    Long roomName,
    LocalTime checkInTime,
    LocalTime checkOutTime,
    LocalDate startDate,
    LocalDate endDate,
    Integer headCount,
    Integer maxHeadCount,
    Integer orderPrice

) {

    public static OrderItemResponse from(OrderItem orderItem) {
        return OrderItemResponse.builder()
            //.accommodationId().accommodationName()
            .roomId(orderItem.getRoom().getId())
            //.roomName()
            //.checkInTime().checkOutTime()
            .startDate(orderItem.getStartDate()).endDate(orderItem.getEndDate())
            .headCount(orderItem.getHeadCount())
            //.maxHeadCount()
            .orderPrice(orderItem.getPrice())
            .build();
    }

}
