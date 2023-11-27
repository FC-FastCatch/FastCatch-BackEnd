package kr.co.fastcampus.fastcatch.domain.accommodation.dto.response;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Room;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.RoomImage;
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
    RoomOptionResponse roomOption,
    List<RoomImageResponse> images
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
            .images(convertEntityToDto(room.getRoomImages()))
            .build();
    }

    private static List<RoomImageResponse> convertEntityToDto(List<RoomImage> images) {
        return images.stream().map(RoomImageResponse::from).collect(Collectors.toList());
    }
}
