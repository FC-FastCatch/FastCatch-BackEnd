package kr.co.fastcampus.fastcatch.common.exception;

import kr.co.fastcampus.fastcatch.common.response.ErrorCode;

public class AlreadyOrderCanceledException extends BaseException {

    public AlreadyOrderCanceledException() {
        super(ErrorCode.ALREADY_ORDER_CANCELED.getErrorMsg());
    }

}
