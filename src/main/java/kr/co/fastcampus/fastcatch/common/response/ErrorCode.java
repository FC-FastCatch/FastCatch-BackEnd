package kr.co.fastcampus.fastcatch.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    COMMON_ENTITY_NOT_FOUND("존재하지 않는 엔티티입니다."),
    DUPLICATED_REQUEST("중복된 요청입니다."),
    INVALID_DATE_RANGE("종료일이 시작일을 앞설 수 없습니다."),
    PAST_DATE("과거 날짜를 선택할 수 없습니다.")
    ;

    private final String errorMsg;

    public String getErrorMsg(Object... arg) {
        return String.format(errorMsg, arg);
    }
}
