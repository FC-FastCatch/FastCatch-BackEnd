package kr.co.fastcampus.fastcatch.common.exception;

import kr.co.fastcampus.fastcatch.common.response.ErrorCode;

public class InvalidTokenException extends BaseException {

    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN.getErrorMsg());
    }

}
