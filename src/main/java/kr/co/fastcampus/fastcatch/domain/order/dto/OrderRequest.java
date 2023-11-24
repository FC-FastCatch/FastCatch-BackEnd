package kr.co.fastcampus.fastcatch.domain.order.dto;

import java.util.List;
import java.util.Objects;
import kr.co.fastcampus.fastcatch.domain.order.entity.Order;
import kr.co.fastcampus.fastcatch.domain.order.entity.OrderStatus;

public record OrderRequest(
    Boolean ageConsent,
    String reservationPersonName,
    String reservationPhoneNumber,
    Integer totalPrice,
    List<OrderItemRequest> orderItemRequests) {

    public OrderRequest {
        Objects.requireNonNull(ageConsent, "연령 동의 여부를 입력하세요.");
        Objects.requireNonNull(reservationPersonName, "예약자 이름을 입력하세요.");
        Objects.requireNonNull(reservationPhoneNumber, "예약자 휴대폰 번호를 입력하세요.");
        Objects.requireNonNull(totalPrice, "주문 전체 가격을 입력하세요.");
        Objects.requireNonNull(orderItemRequests, "주문 아이템 목록을 입력하세요.");
    }

    public Order toEntity() {
        return Order.builder()
            .reservationPersonName(this.reservationPersonName)
            .reservationPersonName(this.reservationPhoneNumber)
            .totalPrice(this.totalPrice)
            .orderStatus(OrderStatus.COMPLETED)
            .build();
    }
}
