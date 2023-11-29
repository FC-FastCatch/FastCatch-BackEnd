package kr.co.fastcampus.fastcatch.domain.cart.dto.response;

import java.util.List;
import kr.co.fastcampus.fastcatch.domain.cart.entity.CartItem;
import lombok.Builder;

@Builder
public record CartItemListResponse(
    Long accommodationId,
    String accommodationName,
    List<CartItemResponse> rooms
) {

    public static CartItemListResponse from (CartItem cartItem) {
        return CartItemListResponse.builder()
            .accommodationId(cartItem.getRoom().getAccommodation().getId())
            .accommodationName(cartItem.getRoom().getAccommodation().getName())
            .rooms(List.of(CartItemResponse.from(cartItem)))
            .build();
    }
}
