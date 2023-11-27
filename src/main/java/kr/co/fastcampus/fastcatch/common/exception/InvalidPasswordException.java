package kr.co.fastcampus.fastcatch.common.exception;

import kr.co.fastcampus.fastcatch.common.response.ErrorCode;

public class InvalidPasswordException extends BaseException {

    public InvalidPasswordException() { super(ErrorCode.INVALID_PASSWORD.getErrorMsg()); }
}
