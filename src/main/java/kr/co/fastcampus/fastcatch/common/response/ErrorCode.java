package kr.co.fastcampus.fastcatch.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    COMMON_ENTITY_NOT_FOUND("존재하지 않는 엔티티입니다."),
    ACCOMMODATION_NOT_FOUND("존재하지 않는 숙박 장소입니다."),
    ROOM_NOT_FOUND("존재하지 않는 객실입니다."),
    DUPLICATED_REQUEST("중복된 요청입니다."),
    CART_NOT_FOUND("해당 하는 장바구니가 없습니다."),
    CART_ITEM_NOT_FOUND("해당 하는 장바구니 아이템이 없습니다."),
    PAST_DATE("현재 날짜 보다 과거의 날짜는 예약 할 수 없습니다."),
    INVALID_DATE_RANGE("시작 날짜가 종료 날짜 보다 늦습니다."),
    INVALID_HEAD_RANGE("해당 인원 수로 예약할 수 없습니다.")
    ;

    private final String errorMsg;

    public String getErrorMsg(Object... arg) {
        return String.format(errorMsg, arg);
    }
}
