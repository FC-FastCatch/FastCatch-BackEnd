package kr.co.fastcampus.fastcatch.common.exception;

import kr.co.fastcampus.fastcatch.common.response.ErrorCode;

public class InvaildHeadRangeException extends BaseException {

    public InvaildHeadRangeException() {
        super(ErrorCode.INVALID_HEAD_RANGE.getErrorMsg());
    }

}
