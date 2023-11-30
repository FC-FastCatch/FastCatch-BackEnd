package kr.co.fastcampus.fastcatch.domain.accommodation.dto.response;

import java.util.List;
import lombok.Builder;
import org.springframework.data.domain.Page;

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
            .totalPages(dto.getTotalPages())
            .totalElements(dto.getTotalElements())
            .isFirst(dto.isFirst())
            .isLast(dto.isLast())
            .build();
    }
}
