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
    INVALID_DATE_RANGE("종료일이 시작일을 앞설 수 없습니다."),
    PAST_DATE("과거 날짜를 선택할 수 없습니다."),
    DUPLICATED_EMAIL("이미 등록된 이메일입니다."),
    DUPLICATED_NICKNAME("이미 사용중인 닉네임입니다.");

    private final String errorMsg;

    public String getErrorMsg(Object... arg) {
        return String.format(errorMsg, arg);
    }
}
