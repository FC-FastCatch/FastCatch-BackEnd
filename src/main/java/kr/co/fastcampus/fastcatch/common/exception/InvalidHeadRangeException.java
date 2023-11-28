package kr.co.fastcampus.fastcatch.common.exception;

import kr.co.fastcampus.fastcatch.common.response.ErrorCode;

public class InvalidHeadRangeException extends BaseException {

    public InvalidHeadRangeException() {
        super(ErrorCode.INVALID_HEAD_RANGE.getErrorMsg());
    }

}
