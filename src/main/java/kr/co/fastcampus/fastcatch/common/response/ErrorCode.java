package kr.co.fastcampus.fastcatch.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    COMMON_ENTITY_NOT_FOUND("존재하지 않는 엔티티입니다."),
    ;

    private final String errorMsg;

    public String getErrorMsg(Object... arg) {
        return String.format(errorMsg, arg);
    }
}
