package kr.co.fastcampus.fastcatch.domain.order.dto;

import java.util.Objects;

public record OrderItemRequest(
    Long roomId,
    String startDate,
    String endDate,
    Integer headCount,
    Integer orderPrice) {

    public OrderItemRequest {
        Objects.requireNonNull(roomId, "객실 ID를 입력하세요.");
        Objects.requireNonNull(startDate, "입실일을 입력하세요.");
        Objects.requireNonNull(endDate, "퇴실일을 입력하세요.");
        Objects.requireNonNull(headCount, "인원 수를 입력하세요.");
        Objects.requireNonNull(orderPrice, "주문 가격을 입력하세요.");
    }
}
