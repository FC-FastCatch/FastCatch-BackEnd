package kr.co.fastcampus.fastcatch.domain.order.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record OrdersResponse(
    List<OrderPageResponse> orders
) {

    public static OrdersResponse from(List<OrderPageResponse> orders) {
        return OrdersResponse.builder().orders(orders).build();
    }
}
