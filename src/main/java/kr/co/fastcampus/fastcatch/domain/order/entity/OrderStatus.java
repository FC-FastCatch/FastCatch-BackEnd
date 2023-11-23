package kr.co.fastcampus.fastcatch.domain.order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    RESERVED("예약 완료"),
    USED("사용 완료"),
    CANCELED("취소");

    private final String description;
}
