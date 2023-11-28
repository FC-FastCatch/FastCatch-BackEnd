package kr.co.fastcampus.fastcatch.common.exception;

import kr.co.fastcampus.fastcatch.common.response.ErrorCode;

public class OrderUnauthorizedException extends BaseException {
    public OrderUnauthorizedException() {
        super(ErrorCode.ORDER_UNAUTHORIZED.getErrorMsg());
    }

}
