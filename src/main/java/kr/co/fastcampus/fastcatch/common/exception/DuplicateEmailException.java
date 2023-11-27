package kr.co.fastcampus.fastcatch.common.exception;

import kr.co.fastcampus.fastcatch.common.response.ErrorCode;

public class DuplicateEmailException extends BaseException {

    public DuplicateEmailException() { super(ErrorCode.DUPLICATE_EMAIL.getErrorMsg()); }
}
