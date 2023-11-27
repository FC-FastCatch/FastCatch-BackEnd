package kr.co.fastcampus.fastcatch.domain.accommodation.dto.request;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalTime;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Accommodation;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Room;
import org.springframework.format.annotation.DateTimeFormat;

public record RoomSaveRequest(
    @NotBlank(message = "이름을 필수로 입력하셔야 합니다.")
    String name,
    @NotBlank(message = "가격을 필수로 입력하셔야 합니다.")
    int price,
    @NotBlank(message = "기준 인원수를 필수로 입력하셔야 합니다.")
    int baseHeadCount,
    @NotBlank(message = "최대 인원수를 필수로 입력하셔야 합니다.")
    int maxHeadCount,
    @NotBlank(message = "방 소개를 필수로 입력하셔야 합니다.")
    String description,
    @DateTimeFormat(pattern = "HH:mm")
    @NotBlank(message = "체크인 시간을 필수로 입력하셔야 합니다.")
    LocalTime checkInTime,
    @DateTimeFormat(pattern = "HH:mm")
    @NotBlank(message = "체크아웃 시간을 필수로 입력하셔야 합니다.")
    LocalTime checkOutTime
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
            .build();
    }
}
