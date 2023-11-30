package kr.co.fastcampus.fastcatch.common.exception;

import kr.co.fastcampus.fastcatch.common.response.ErrorCode;

public class InvalidHeadCountRangeException extends BaseException {

    public InvalidHeadCountRangeException() {
        super(ErrorCode.INVALID_HEAD_COUNT_RANGE.getErrorMsg());
    }

}
