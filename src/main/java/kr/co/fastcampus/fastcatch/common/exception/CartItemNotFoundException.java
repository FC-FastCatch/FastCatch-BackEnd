package kr.co.fastcampus.fastcatch.common.exception;

import kr.co.fastcampus.fastcatch.common.response.ErrorCode;

public class CartItemNotFoundException extends BaseException {
    public CartItemNotFoundException() {
        super(ErrorCode.CART_ITEM_NOT_FOUND.getErrorMsg());
    }
}
