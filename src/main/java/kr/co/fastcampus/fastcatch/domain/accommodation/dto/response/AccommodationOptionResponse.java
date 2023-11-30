package kr.co.fastcampus.fastcatch.domain.accommodation.dto.response;

import kr.co.fastcampus.fastcatch.domain.accommodation.entity.AccommodationOption;
import lombok.Builder;

@Builder
public record AccommodationOptionResponse(
    Boolean hasSmokingRoom,
    Boolean hasPetRoom,
    Boolean hasParkingLot,
    Boolean hasWifi,
    Boolean hasSwimmingPool,
    Boolean hasGym,
    Boolean hasBreakfast,
    Boolean hasRestaurant,
    Boolean hasCookingRoom
) {

    public static AccommodationOptionResponse from(
        AccommodationOption accommodationOption
    ) {
        return AccommodationOptionResponse.builder()
            .hasSmokingRoom(accommodationOption.getHasSmokingRoom())
            .hasPetRoom(accommodationOption.getHasPetRoom())
            .hasParkingLot(accommodationOption.getHasParkingLot())
            .hasWifi(accommodationOption.getHasWifi())
            .hasSwimmingPool(accommodationOption.getHasSwimmingPool())
            .hasGym(accommodationOption.getHasGym())
            .hasBreakfast(accommodationOption.getHasBreakfast())
            .hasRestaurant(accommodationOption.getHasRestaurant())
            .hasCookingRoom(accommodationOption.getHasCookingRoom())
            .build();
    }
}
