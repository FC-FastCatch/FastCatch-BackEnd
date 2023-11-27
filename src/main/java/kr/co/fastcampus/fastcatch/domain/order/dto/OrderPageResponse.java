package kr.co.fastcampus.fastcatch.domain.order.dto;

import java.util.List;
import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder
public record OrderPageResponse(
    List<OrderResponse> orderResponses,
    String status,
    int pageNum,
    int pageSize
) {

    public static OrderPageResponse from(Page<OrderResponse> orderResponsePage, String status) {
        return OrderPageResponse.builder().orderResponses(orderResponsePage.getContent())
            .status(status)
            .pageNum(orderResponsePage.getNumber() + 1)
            .pageSize(orderResponsePage.getSize())
            .build();
    }
}
