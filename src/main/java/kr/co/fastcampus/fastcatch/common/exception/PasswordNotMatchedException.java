package kr.co.fastcampus.fastcatch.common.exception;

import kr.co.fastcampus.fastcatch.common.response.ErrorCode;

public class PasswordNotMatchedException extends BaseException {

    public PasswordNotMatchedException() {
        super(ErrorCode.PASSWORD_NOT_MATCHED.getErrorMsg());
    }
}
