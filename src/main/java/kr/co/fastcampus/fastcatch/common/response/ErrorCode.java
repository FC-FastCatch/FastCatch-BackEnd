package kr.co.fastcampus.fastcatch.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    COMMON_ENTITY_NOT_FOUND("존재하지 않는 엔티티입니다."),
    ORDER_NOT_FOUND("존재하지 않는 주문 정보입니다."),
    ORDER_UNAUTHORIZED("접근 권한이 없는 주문 정보입니다. "),
    INVALID_ORDER_STATUS("유효하지 않은 주문 상태입니다.")
    ;

    private final String errorMsg;

    public String getErrorMsg(Object... arg) {
        return String.format(errorMsg, arg);
    }
}
