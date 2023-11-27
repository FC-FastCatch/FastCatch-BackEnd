package kr.co.fastcampus.fastcatch.domain.order.dto.response;

import java.util.List;
import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder
public record OrderPageResponse(
    String status,
    int pageNum,
    int pageSize,
    List<OrderResponse> orderResponses
) {

    public static OrderPageResponse from(Page<OrderResponse> orderResponsePage, String status) {
        return OrderPageResponse.builder().orderResponses(orderResponsePage.getContent())
            .status(status)
            .pageNum(orderResponsePage.getNumber())
            .pageSize(orderResponsePage.getSize())
            .build();
    }
}
