package kr.co.fastcampus.fastcatch.domain.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record OrderItemRequest(
    @NotNull(message = "룸 ID를 필수로 입력하셔야 합니다.")
    Long roomId,
    @NotNull(message = "입실일을 필수로 입력하셔야 합니다.")
    LocalDate startDate,
    @NotBlank(message = "퇴실일을 필수로 입력하셔야 합니다.")
    LocalDate endDate,
    @NotNull(message = "인원수를 필수로 입력하셔야 합니다.")
    Integer headCount,
    @NotNull(message = "주문 가격을 필수로 입력하셔야 합니다.")
    Integer orderPrice
) {

}
