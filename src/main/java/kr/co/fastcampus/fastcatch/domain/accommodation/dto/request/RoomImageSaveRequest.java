package kr.co.fastcampus.fastcatch.domain.accommodation.dto.request;

import java.time.LocalDate;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Room;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.RoomImage;

public record RoomImageSaveRequest(
    String fileName,
    LocalDate stayDate
) {

    public RoomImage toEntity(Room room) {
        return RoomImage.builder()
            .room(room)
            .fileName(fileName)
            .build();
    }
}
