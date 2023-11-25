package kr.co.fastcampus.fastcatch.domain.accommodation.dto.request;

import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Room;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.RoomOption;

public record RoomOptionSaveRequest(
    Long roomId,
    boolean canSmoking,
    boolean petAccompanying,
    boolean cityView,
    boolean oceanView,
    boolean hasNetflix,
    boolean canCooking
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
