package kr.co.fastcampus.fastcatch.common.exception;

import kr.co.fastcampus.fastcatch.common.response.ErrorCode;

public class TokenNotMatchedException extends BaseException {

    public TokenNotMatchedException() {
        super(ErrorCode.TOKEN_NOT_MATCHED.getErrorMsg());
    }

}
