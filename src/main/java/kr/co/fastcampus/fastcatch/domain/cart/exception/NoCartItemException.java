package kr.co.fastcampus.fastcatch.domain.cart.exception;

import kr.co.fastcampus.fastcatch.common.exception.BaseException;

public class NoCartItemException extends BaseException {

    public NoCartItemException() {
        super(CartItemErrorCode.NO_CART_ITEM.getErrorMsg());
    }

}
