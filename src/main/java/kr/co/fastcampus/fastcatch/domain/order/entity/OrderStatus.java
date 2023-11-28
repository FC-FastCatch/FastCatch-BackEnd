package kr.co.fastcampus.fastcatch.domain.order.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    COMPLETED("주문 완료"),
    CANCELED("주문 취소");

    private final String description;
}
