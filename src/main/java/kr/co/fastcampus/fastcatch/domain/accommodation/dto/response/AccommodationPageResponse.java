package kr.co.fastcampus.fastcatch.domain.accommodation.dto.response;

import java.util.List;
import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder
public record AccommodationPageResponse(
    List<AccommodationSummaryResponse> accommodations,
    int pageNum,
    int pageSize,
    int totalPages,
    long totalElements,
    boolean isFirst,
    boolean isLast

) {

    public static AccommodationPageResponse from(Page<AccommodationSummaryResponse> dto) {
        return AccommodationPageResponse.builder()
            .accommodations(dto.getContent())
            .pageNum(dto.getNumber() + 1)
            .pageSize(dto.getSize())
            .totalElements(dto.getTotalElements())
            .isFirst(dto.isFirst())
            .isLast(dto.isLast())
            .build();
    }
}
