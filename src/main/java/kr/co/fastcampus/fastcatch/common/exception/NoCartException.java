package kr.co.fastcampus.fastcatch.common.exception;

import kr.co.fastcampus.fastcatch.common.response.ErrorCode;

public class NoCartException extends BaseException {

    public NoCartException() {
        super(ErrorCode.NO_CART.getErrorMsg());
    }

}
