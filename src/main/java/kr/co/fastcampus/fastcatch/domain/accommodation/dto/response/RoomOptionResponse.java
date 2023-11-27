package kr.co.fastcampus.fastcatch.domain.accommodation.dto.response;

import kr.co.fastcampus.fastcatch.domain.accommodation.entity.RoomOption;
import lombok.Builder;

@Builder
public record RoomOptionResponse(
    boolean canSmoking,
    boolean petAccompanying,
    boolean cityView,
    boolean oceanView,
    boolean hasNetflix,
    boolean canCooking
) {

    public static RoomOptionResponse from(RoomOption roomOption) {
        return new RoomOptionResponse(
            roomOption.isCanSmoking(),
            roomOption.isPetAccompanying(),
            roomOption.isCityView(),
            roomOption.isOceanView(),
            roomOption.isHasNetflix(),
            roomOption.isCanCooking()
        );
    }
}
