package kr.co.fastcampus.fastcatch.domain.cart.dto.response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import kr.co.fastcampus.fastcatch.domain.cart.entity.CartItem;
import lombok.Builder;

@Builder
public record CartItemResponse(
    Long cartItemId,
    Long roomId,
    LocalDate startDate,
    LocalDate endDate,
    Integer headCount,
    Integer price
) {
    public static CartItemResponse from(
        CartItem cartItem
    ) {
        return CartItemResponse.builder()
            .cartItemId(cartItem.getCartItemId())
            .roomId(cartItem.getRoom().getId())
            .startDate(cartItem.getStartDate())
            .endDate(cartItem.getEndDate())
            .headCount(cartItem.getHeadCount())
            .price(cartItem.getPrice())
            .build();
    }
}
