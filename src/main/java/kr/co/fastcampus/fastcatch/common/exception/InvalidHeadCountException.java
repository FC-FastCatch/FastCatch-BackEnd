package kr.co.fastcampus.fastcatch.common.exception;

import kr.co.fastcampus.fastcatch.common.response.ErrorCode;

public class InvalidHeadCountException extends BaseException {

    public InvalidHeadCountException() {
        super(ErrorCode.INVALID_HEAD_COUNT.getErrorMsg());
    }

}
