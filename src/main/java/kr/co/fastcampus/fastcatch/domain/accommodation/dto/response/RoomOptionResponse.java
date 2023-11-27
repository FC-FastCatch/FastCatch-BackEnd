package kr.co.fastcampus.fastcatch.domain.accommodation.dto.response;

import kr.co.fastcampus.fastcatch.domain.accommodation.entity.RoomOption;
import lombok.Builder;

@Builder
public record RoomOptionResponse(
    Boolean canSmoking,
    Boolean petAccompanying,
    Boolean cityView,
    Boolean oceanView,
    Boolean hasNetflix,
    Boolean canCooking
) {

    public static RoomOptionResponse from(RoomOption roomOption) {
        return new RoomOptionResponse(
            roomOption.getCanSmoking(),
            roomOption.getPetAccompanying(),
            roomOption.getCityView(),
            roomOption.getOceanView(),
            roomOption.getHasNetflix(),
            roomOption.getCanCooking()
        );
    }
}
