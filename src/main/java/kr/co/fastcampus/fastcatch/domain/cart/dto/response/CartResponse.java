package kr.co.fastcampus.fastcatch.domain.cart.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record CartResponse(
    Integer totalPrice,
    List<CartItemListResponse> cartItemResponseList
) {
    public static CartResponse from(
        List<CartItemListResponse> responses
    ) {
        return CartResponse.builder()
            .totalPrice(sumTotalPrice(responses))
            .cartItemResponseList(responses)
            .build();
    }

    private static Integer sumTotalPrice(List<CartItemListResponse> responses) {
        int totalPrice = 0;
        for (CartItemListResponse response : responses) {
            for (CartItemResponse itemResponse : response.rooms()) {
                totalPrice += itemResponse.price();
            }
        }
        return totalPrice;
    }

}
