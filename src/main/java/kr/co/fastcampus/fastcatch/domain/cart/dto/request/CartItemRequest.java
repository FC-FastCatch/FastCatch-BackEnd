package kr.co.fastcampus.fastcatch.domain.cart.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import kr.co.fastcampus.fastcatch.domain.accommodation.entity.Room;
import kr.co.fastcampus.fastcatch.domain.cart.entity.CartItem;

public record CartItemRequest(
    @NotNull(message = "roomId는 필수 입력입니다.")
    Long roomId,
    @NotNull(message = "startDate는 필수 입력입니다.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate startDate,
    @NotNull(message = "endDate는 필수 입력입니다.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate endDate,
    @NotNull(message = "headCount는 필수 입력입니다.")
    Integer headCount,
    @NotNull(message = "orderPrice는 필수 입력입니다.")
    Integer orderPrice
) {
    public CartItem toEntity(Room room) {
        return CartItem.builder()
            .room(room)
            .startDate(startDate)
            .endDate(endDate)
            .headCount(headCount)
            .price(orderPrice)
            .build();
    }
}
