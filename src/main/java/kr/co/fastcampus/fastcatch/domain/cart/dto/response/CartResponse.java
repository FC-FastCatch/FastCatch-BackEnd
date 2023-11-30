package kr.co.fastcampus.fastcatch.domain.cart.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record CartResponse(
    List<CartItemListResponse> cartItemResponseList
) {
    public static CartResponse setCartItemResponseList(
        List<CartItemListResponse> cartItemResponseList
    ) {
        return CartResponse.builder()
            .cartItemResponseList(cartItemResponseList)
            .build();
    }

}
