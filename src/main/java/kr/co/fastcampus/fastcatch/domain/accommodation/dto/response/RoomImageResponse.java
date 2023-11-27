package kr.co.fastcampus.fastcatch.domain.accommodation.dto.response;

import kr.co.fastcampus.fastcatch.domain.accommodation.entity.RoomImage;
import lombok.Builder;

@Builder
public record RoomImageResponse(
    String fileName
) {

    public static RoomImageResponse from(RoomImage roomImage) {
        return RoomImageResponse.builder()
            .fileName(roomImage.getFileName())
            .build();
    }
}
