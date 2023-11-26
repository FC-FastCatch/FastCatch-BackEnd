package kr.co.fastcampus.fastcatch.domain.accommodation.dto.request;

import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Room;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.RoomOption;

public record RoomOptionSaveRequest(
    Long roomId,
    Boolean canSmoking,
    Boolean petAccompanying,
    Boolean cityView,
    Boolean oceanView,
    Boolean hasNetflix,
    Boolean canCooking
) {

    public RoomOption toEntity(Room room) {
        return RoomOption.builder()
            .room(room)
            .canSmoking(canSmoking)
            .petAccompanying(petAccompanying)
            .cityView(cityView)
            .oceanView(oceanView)
            .hasNetflix(hasNetflix)
            .canCooking(canCooking)
            .build();
    }
}
