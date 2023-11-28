package kr.co.fastcampus.fastcatch.common.exception;

import kr.co.fastcampus.fastcatch.common.response.ErrorCode;

public class OrderNotFoundException extends BaseException {

    public OrderNotFoundException() {
        super(ErrorCode.ORDER_NOT_FOUND.getErrorMsg());
    }
}
