package kr.co.fastcampus.fastcatch.domain.order.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import kr.co.fastcampus.fastcatch.domain.cart.entity.CartItem;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public record OrderItemRequest(
    @NotNull(message = "룸 ID를 필수로 입력하셔야 합니다.")
    Long roomId,
    @DateTimeFormat(iso = ISO.DATE)
    @NotNull(message = "입실일을 필수로 입력하셔야 합니다.")
    LocalDate startDate,
    @DateTimeFormat(iso = ISO.DATE)
    @NotBlank(message = "퇴실일을 필수로 입력하셔야 합니다.")
    LocalDate endDate,
    @NotNull(message = "인원수를 필수로 입력하셔야 합니다.")
    Integer headCount,
    @NotNull(message = "주문 가격을 필수로 입력하셔야 합니다.")
    Integer orderPrice
) {

    public static OrderItemRequest fromCartItem(CartItem cartItem) {
        return new OrderItemRequest(cartItem.getRoom().getId(), cartItem.getStartDate(),
            cartItem.getEndDate(), cartItem.getHeadCount(), cartItem.getPrice());
    }
}