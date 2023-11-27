package kr.co.fastcampus.fastcatch.domain.order.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
import kr.co.fastcampus.fastcatch.domain.order.entity.OrderItem;
import lombok.Builder;

@Builder
public record OrderItemResponse(
    Long accommodationId,
    String accommodationName,
    Long roomId,
    String roomName,
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
            .accommodationId(orderItem.getRoom().getAccommodation().getId())
            .accommodationName(orderItem.getRoom().getAccommodation().getName())
            .roomId(orderItem.getRoom().getId())
            .roomName(orderItem.getRoom().getName())
            .checkInTime(orderItem.getRoom().getCheckInTime())
            .checkOutTime(orderItem.getRoom().getCheckOutTime())
            .startDate(orderItem.getStartDate()).endDate(orderItem.getEndDate())
            .headCount(orderItem.getHeadCount())
            .maxHeadCount(orderItem.getRoom().getMaxHeadCount())
            .orderPrice(orderItem.getPrice())
            .build();
    }

}