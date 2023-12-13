package kr.co.fastcampus.fastcatch.common.exception;

import kr.co.fastcampus.fastcatch.common.response.ErrorCode;

public class ExpiredTokenException extends BaseException {

    public ExpiredTokenException() {
        super(ErrorCode.EXPIRED_TOKEN.getErrorMsg());
    }

}
