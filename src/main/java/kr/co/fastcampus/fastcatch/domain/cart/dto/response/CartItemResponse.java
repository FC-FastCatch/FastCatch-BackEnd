package kr.co.fastcampus.fastcatch.domain.cart.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import kr.co.fastcampus.fastcatch.domain.cart.entity.CartItem;
import lombok.Builder;

@Builder
public record CartItemResponse(
    Long cartItemId,
    Long roomId,
    String accommodationName,
    LocalDate startDate,
    LocalDate endDate,
    Integer headCount,
    Integer price,
    @JsonFormat(pattern = "HH:mm")
    LocalTime checkInTime,
    @JsonFormat(pattern = "HH:mm")
    LocalTime checkOutTime,
    Integer maxHeadCount
) {
    public static CartItemResponse from(
        CartItem cartItem
    ) {
        return CartItemResponse.builder()
            .cartItemId(cartItem.getCartItemId())
            .roomId(cartItem.getRoom().getId())
            .accommodationName(cartItem.getRoom().getAccommodation().getName())
            .startDate(cartItem.getStartDate())
            .endDate(cartItem.getEndDate())
            .headCount(cartItem.getHeadCount())
            .price(cartItem.getPrice())
            .checkInTime(cartItem.getRoom().getCheckInTime())
            .checkOutTime(cartItem.getRoom().getCheckOutTime())
            .maxHeadCount(cartItem.getRoom().getMaxHeadCount())
            .build();
    }
}
