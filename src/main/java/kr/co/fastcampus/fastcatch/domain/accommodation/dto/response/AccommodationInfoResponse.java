package kr.co.fastcampus.fastcatch.domain.accommodation.dto.response;

import java.util.List;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Category;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Region;
import lombok.Builder;

@Builder
public record AccommodationInfoResponse(
    Long id,
    String name,
    String address,
    String phoneNumber,
    String longitude,
    String latitude,
    Region region,
    String description,
    Category category,
    String image,
    AccommodationOptionResponse accommodationOption,
    List<RoomResponse> rooms
) {

    public static AccommodationInfoResponse from(
        Accommodation accommodation, List<RoomResponse> rooms
    ) {
        return AccommodationInfoResponse.builder()
            .id(accommodation.getId())
            .name(accommodation.getName())
            .address(accommodation.getAddress())
            .phoneNumber(accommodation.getPhoneNumber())
            .longitude(accommodation.getLongitude())
            .latitude(accommodation.getLatitude())
            .region(accommodation.getRegion())
            .description(accommodation.getDescription())
            .category(accommodation.getCategory())
            .image(accommodation.getImage())
            .accommodationOption(
                AccommodationOptionResponse.from(accommodation.getAccommodationOption())
            )
            .rooms(rooms)
            .build();
    }
}
