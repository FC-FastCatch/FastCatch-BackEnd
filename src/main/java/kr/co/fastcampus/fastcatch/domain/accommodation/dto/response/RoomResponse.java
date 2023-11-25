package kr.co.fastcampus.fastcatch.domain.accommodation.dto.response;

import java.time.LocalTime;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Room;
import lombok.Builder;

@Builder
public record RoomResponse(
    Long roomId,
    String name,
    int price,
    int baseHeadCount,
    int maxHeadCount,
    String description,
    LocalTime checkInTime,
    LocalTime checkOutTime,
    boolean soldOut,
    RoomOptionResponse roomOption
) {

    public static RoomResponse from(Room room, boolean isSoldOut) {
        return RoomResponse.builder()
            .roomId(room.getId())
            .name(room.getName())
            .price(room.getPrice())
            .baseHeadCount(room.getBaseHeadCount())
            .maxHeadCount(room.getMaxHeadCount())
            .description(room.getDescription())
            .checkInTime(room.getCheckInTime())
            .checkOutTime(room.getCheckOutTime())
            .soldOut(isSoldOut)
            .roomOption(RoomOptionResponse.from(room.getRoomOption()))
            .build();
    }
}
