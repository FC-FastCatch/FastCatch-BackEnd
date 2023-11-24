package kr.co.fastcampus.fastcatch.common.utility.exception;

import kr.co.fastcampus.fastcatch.common.exception.BaseException;

public class StartDateIsLaterThanEndDateException extends BaseException {

    public StartDateIsLaterThanEndDateException() {
        super(AvailableUtilErrorCode.START_DATE_IS_LATER_THAN_END_DATE.getErrorMsg());
    }

}
