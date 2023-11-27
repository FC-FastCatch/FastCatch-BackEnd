package kr.co.fastcampus.fastcatch.domain.accommodation.dto.response;

import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Category;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Region;
import lombok.Builder;

@Builder
public record AccommodationSearchResponse(
    Long id,
    String name,
    String address,
    Region region,
    Category category,
    int lowestPrice,
    String image,
    AccommodationOptionResponse accommodationOption
) {

    public static AccommodationSearchResponse from(Accommodation accommodation) {
        return AccommodationSearchResponse.builder()
            .id(accommodation.getId())
            .name(accommodation.getName())
            .region(accommodation.getRegion())
            .category(accommodation.getCategory())
            .lowestPrice(accommodation.getLowestPrice())
            .image(accommodation.getImage())
            .accommodationOption(
                AccommodationOptionResponse.from(accommodation.getAccommodationOption())
            )
            .build();
    }
}
