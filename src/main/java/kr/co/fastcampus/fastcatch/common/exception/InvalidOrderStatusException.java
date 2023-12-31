package kr.co.fastcampus.fastcatch.common.exception;

import kr.co.fastcampus.fastcatch.common.response.ErrorCode;

public class InvalidOrderStatusException extends BaseException {
    public InvalidOrderStatusException() {
        super(ErrorCode.INVALID_ORDER_STATUS.getErrorMsg());
    }
}
