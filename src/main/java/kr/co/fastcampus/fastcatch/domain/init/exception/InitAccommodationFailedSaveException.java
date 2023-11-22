package kr.co.fastcampus.fastcatch.domain.init.exception;

import kr.co.fastcampus.fastcatch.common.exception.BaseException;

public class InitAccommodationFailedSaveException extends BaseException {

    public InitAccommodationFailedSaveException() {
        super(InitAccommodationExceptionCode.FAILED_SAVE.getErrorMsg());
    }
}
