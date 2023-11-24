package kr.co.fastcampus.fastcatch.common.utility.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AvailableUtilErrorCode {
    START_DATE_IS_LATER_THAN_END_DATE("시작 날짜가 종료 날짜 보다 늦습니다."),
    ;

    private final String errorMsg;

    public String getErrorMsg(Object... arg) {
        return String.format(errorMsg, arg);
    }
}
