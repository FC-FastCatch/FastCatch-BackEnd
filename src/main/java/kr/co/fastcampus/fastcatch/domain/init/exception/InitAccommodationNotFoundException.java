package kr.co.fastcampus.fastcatch.domain.init.exception;

import kr.co.fastcampus.fastcatch.common.exception.BaseException;

public class InitAccommodationNotFoundException extends BaseException {

    public InitAccommodationNotFoundException() {
        super(InitAccommodationExceptionCode.NOT_FOUND_FILE.getErrorMsg());
    }
}
