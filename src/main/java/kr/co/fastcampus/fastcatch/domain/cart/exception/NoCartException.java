package kr.co.fastcampus.fastcatch.domain.cart.exception;

import kr.co.fastcampus.fastcatch.common.exception.BaseException;
import kr.co.fastcampus.fastcatch.common.utility.exception.AvailableUtilErrorCode;

public class NoCartException extends BaseException {

    public NoCartException() {
        super(CartErrorCode.NO_CART.getErrorMsg());
    }

}
