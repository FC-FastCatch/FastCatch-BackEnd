package kr.co.fastcampus.fastcatch.domain.cart.dto.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.fastcatch.domain.cart.entity.Cart;
import lombok.Builder;

@Builder
public record CartResponse(
    List<CartItemListResponse> cartItemResponseList
) {
    public static CartResponse from(
        Cart cart
    ) {
        return CartResponse.builder()
            .cartItemResponseList(
                Optional.ofNullable(cart.getCartItems())
                    .orElse(new ArrayList<>())
                    .stream().map(CartItemListResponse::from)
                    .toList()
            )
            .build();
    }

    public static CartResponse setCartItemResponseList(
        List<CartItemListResponse> cartItemResponseList
    ){
        return CartResponse.builder()
            .cartItemResponseList(cartItemResponseList)
            .build();
    }

}
