package kr.co.fastcampus.fastcatch.common.exception;

import kr.co.fastcampus.fastcatch.common.response.ErrorCode;

public class OrderUnauthorizedException extends RuntimeException {
    public OrderUnauthorizedException() {
        super(ErrorCode.ORDER_UNAUTHORIZED.getErrorMsg());
    }

}
