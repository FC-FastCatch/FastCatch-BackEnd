package kr.co.fastcampus.fastcatch.common.exception;

import kr.co.fastcampus.fastcatch.common.response.ErrorCode;

public class DuplicatedRequest extends BaseException {

    public DuplicatedRequest() {
        super(ErrorCode.DUPLICATED_REQUEST.getErrorMsg());
    }
}
