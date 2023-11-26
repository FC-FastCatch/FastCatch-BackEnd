package kr.co.fastcampus.fastcatch.domain.order.dto;

import java.util.List;
import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder
public record OrderPageResponse(
    List<OrderResponse> orderResponses,
    int pageNum,
    int pageSize
) {

    public static OrderPageResponse from(Page<OrderResponse> orderResponsePage) {
        return OrderPageResponse.builder().orderResponses(orderResponsePage.getContent())
            .pageNum(orderResponsePage.getNumber()+1)
            .pageSize(orderResponsePage.getSize())
            .build();
    }
}
