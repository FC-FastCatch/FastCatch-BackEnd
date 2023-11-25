package kr.co.fastcampus.fastcatch.domain.accommodation.dto.response;

import kr.co.fastcampus.fastcatch.domain.accommodation.entity.AccommodationOption;
import lombok.Builder;

@Builder
public record AccommodationOptionResponse(
    boolean hasSmokingRoom,
    boolean hasPetRoom,
    boolean hasParkingLot,
    boolean hasWifi,
    boolean hasSwimmingPool,
    boolean hasGym,
    boolean hasBreakfast,
    boolean hasRestaurant,
    boolean hasCookingRoom
) {

    public static AccommodationOptionResponse from(
        AccommodationOption accommodationOption
    ) {
        return AccommodationOptionResponse.builder()
            .hasSmokingRoom(accommodationOption.isHasSmokingRoom())
            .hasPetRoom(accommodationOption.isHasPetRoom())
            .hasParkingLot(accommodationOption.isHasParkingLot())
            .hasWifi(accommodationOption.isHasWifi())
            .hasSwimmingPool(accommodationOption.isHasSwimmingPool())
            .hasGym(accommodationOption.isHasGym())
            .hasBreakfast(accommodationOption.isHasBreakfast())
            .hasRestaurant(accommodationOption.isHasRestaurant())
            .hasCookingRoom(accommodationOption.isHasCookingRoom())
            .build();
    }
}
