package kr.co.fastcampus.fastcatch.domain.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import kr.co.fastcampus.fastcatch.domain.member.entity.Member;
import kr.co.fastcampus.fastcatch.domain.order.entity.Order;
import kr.co.fastcampus.fastcatch.domain.order.entity.OrderStatus;

public record OrderRequest(
    @NotNull(message = "나이 동의 여부를 필수로 입력하셔야 합니다.")
    Boolean ageConsent,
    @NotBlank(message = "예약자 이름을 필수로 입력하셔야 합니다.")
    String reservationPersonName,
    @NotBlank(message = "예약자 휴대폰번호를 필수로 입력하셔야 합니다.")
    String reservationPhoneNumber,
    @NotNull(message = "주문 전체 가격을 필수로 입력하셔야 합니다.")
    Integer totalPrice,
    @NotNull(message = "주문 아이템을 필수로 입력하셔야 합니다.")
    List<OrderItemRequest> orderItemRequests) {

    public Order toEntity(Member member) {
        return Order.builder()
            .member(member)
            .reservationPersonName(this.reservationPersonName)
            .reservationPersonName(this.reservationPhoneNumber)
            .totalPrice(this.totalPrice)
            .orderStatus(OrderStatus.COMPLETED)
            .build();
    }
}
