package kr.co.fastcampus.fastcatch.common.exception;

import kr.co.fastcampus.fastcatch.common.response.ErrorCode;

public class AccommodationNotFoundException extends BaseException {
    public AccommodationNotFoundException() {
        super(ErrorCode.ACCOMMODATION_NOT_FOUND.getErrorMsg());
    }
}
