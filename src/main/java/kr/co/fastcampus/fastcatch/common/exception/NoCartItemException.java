package kr.co.fastcampus.fastcatch.common.exception;

import kr.co.fastcampus.fastcatch.common.response.ErrorCode;

public class NoCartItemException extends BaseException {

    public NoCartItemException() {
        super(ErrorCode.NO_CART_ITEM.getErrorMsg());
    }

}
