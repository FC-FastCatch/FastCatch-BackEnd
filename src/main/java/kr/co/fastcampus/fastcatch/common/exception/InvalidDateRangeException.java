package kr.co.fastcampus.fastcatch.common.exception;

import kr.co.fastcampus.fastcatch.common.response.ErrorCode;

public class InvalidDateRangeException extends BaseException {
    public InvalidDateRangeException() {
        super(ErrorCode.INVALID_DATE_RANGE.getErrorMsg());
    }
}
