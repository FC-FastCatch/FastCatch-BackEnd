package kr.co.fastcampus.fastcatch.common.exception;

import kr.co.fastcampus.fastcatch.common.response.ErrorCode;

public class DuplicatedEmailException extends BaseException {

    public DuplicatedEmailException() {
        super(ErrorCode.DUPLICATED_EMAIL.getErrorMsg());
    }
}
