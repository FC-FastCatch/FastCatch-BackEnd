package kr.co.fastcampus.fastcatch.common.exception;

import kr.co.fastcampus.fastcatch.common.response.ErrorCode;

public class CartNotFoundException extends BaseException {
    public CartNotFoundException() {
        super(ErrorCode.CART_NOT_FOUND.getErrorMsg());
    }
}
