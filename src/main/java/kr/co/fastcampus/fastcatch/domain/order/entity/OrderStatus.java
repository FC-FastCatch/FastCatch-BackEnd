package kr.co.fastcampus.fastcatch.domain.order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    COMPLETED("주문 완료"),
    CANCELED("주문 취소");

    private final String description;
}
