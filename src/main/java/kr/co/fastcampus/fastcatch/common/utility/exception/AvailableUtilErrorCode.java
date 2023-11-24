package kr.co.fastcampus.fastcatch.common.utility.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AvailableUtilErrorCode {
    DATE_IS_LATER_THAN_CURRENT("현재 날짜 보다 과거의 날짜는 예약 할 수 없습니다."),
    START_DATE_IS_LATER_THAN_END_DATE("시작 날짜가 종료 날짜 보다 늦습니다.")
    ;

    private final String errorMsg;

    public String getErrorMsg(Object... arg) {
        return String.format(errorMsg, arg);
    }
}
