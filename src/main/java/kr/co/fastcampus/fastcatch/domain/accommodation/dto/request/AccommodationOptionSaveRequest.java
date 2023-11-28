package kr.co.fastcampus.fastcatch.domain.accommodation.dto.request;

import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.AccommodationOption;

public record AccommodationOptionSaveRequest(
    Long accommodationId,
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

    public AccommodationOption toEntity(Accommodation accommodation) {
        return AccommodationOption.builder()
            .accommodation(accommodation)
            .hasSmokingRoom(hasSmokingRoom)
            .hasPetRoom(hasPetRoom)
            .hasParkingLot(hasParkingLot)
            .hasWifi(hasWifi)
            .hasSwimmingPool(hasSwimmingPool)
            .hasGym(hasGym)
            .hasBreakfast(hasBreakfast)
            .hasRestaurant(hasRestaurant)
            .hasCookingRoom(hasCookingRoom)
            .build();
    }
}
