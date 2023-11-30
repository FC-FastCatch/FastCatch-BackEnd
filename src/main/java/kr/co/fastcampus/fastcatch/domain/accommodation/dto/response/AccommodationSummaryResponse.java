package kr.co.fastcampus.fastcatch.domain.accommodation.dto.response;

import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Category;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Region;
import lombok.Builder;

@Builder
public record AccommodationSummaryResponse(
    Long id,
    String name,
    String address,
    Region region,
    Category category,
    int lowestPrice,
    String image,
    boolean soldOut,
    AccommodationOptionResponse accommodationOption

) {

    public static AccommodationSummaryResponse from(
        Accommodation accommodation, boolean isSoldOut
    ) {
        return AccommodationSummaryResponse.builder()
            .id(accommodation.getId())
            .name(accommodation.getName())
            .address(accommodation.getAddress())
            .region(accommodation.getRegion())
            .category(accommodation.getCategory())
            .lowestPrice(accommodation.getLowestPrice())
            .image(accommodation.getImage())
            .soldOut(isSoldOut)
            .accommodationOption(
                AccommodationOptionResponse.from(accommodation.getAccommodationOption())
            )
            .build();
    }
}
