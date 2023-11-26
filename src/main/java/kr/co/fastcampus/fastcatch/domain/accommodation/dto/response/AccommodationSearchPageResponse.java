package kr.co.fastcampus.fastcatch.domain.accommodation.dto.response;

import lombok.Builder;
import org.springframework.data.domain.Page;
import java.util.List;

@Builder
public record AccommodationSearchPageResponse(
    List<AccommodationSearchResponse> accommodations,
    int pageNum,
    int pageSize,
    int totalPages,
    long totalElements,
    boolean isFirst,
    boolean isLast
) {

    public static AccommodationSearchPageResponse from(Page<AccommodationSearchResponse> dto) {
        return AccommodationSearchPageResponse.builder()
            .accommodations(dto.getContent())
            .pageNum(dto.getNumber() + 1)
            .pageSize(dto.getSize())
            .totalElements(dto.getTotalElements())
            .isFirst(dto.isFirst())
            .isLast(dto.isLast())
            .build();
    }
}
