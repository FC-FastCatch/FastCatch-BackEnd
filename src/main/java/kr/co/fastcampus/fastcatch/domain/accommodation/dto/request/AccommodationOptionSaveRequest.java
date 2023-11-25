package kr.co.fastcampus.fastcatch.domain.accommodation.dto.request;

import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.AccommodationOption;

public record AccommodationOptionSaveRequest(
    Long accommodationId,
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
