package kr.co.fastcampus.fastcatch.common.exception;

import kr.co.fastcampus.fastcatch.common.exception.BaseException;
import kr.co.fastcampus.fastcatch.common.response.ErrorCode;

public class StartDateIsLaterThanEndDateException extends BaseException {

    public StartDateIsLaterThanEndDateException() {
        super(ErrorCode.START_DATE_IS_LATER_THAN_END_DATE.getErrorMsg());
    }

}
