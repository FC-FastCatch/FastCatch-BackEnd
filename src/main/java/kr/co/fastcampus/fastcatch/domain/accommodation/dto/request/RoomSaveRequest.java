package kr.co.fastcampus.fastcatch.domain.accommodation.dto.request;

import java.time.LocalTime;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Room;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.RoomOption;

public record RoomSaveRequest(
    Long accommodationId,
    String name,
    int price,
    int baseHeadCount,
    int maxHeadCount,
    String description,
    LocalTime checkInTime,
    LocalTime checkOutTime,
    RoomOption roomOption
) {

    public Room toEntity(Accommodation accommodation) {
        return Room.builder()
            .accommodation(accommodation)
            .name(name)
            .price(price)
            .baseHeadCount(baseHeadCount)
            .maxHeadCount(maxHeadCount)
            .description(description)
            .checkInTime(checkInTime)
            .checkOutTime(checkOutTime)
            .roomOption(roomOption)
            .build();
    }
}
