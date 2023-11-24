package kr.co.fastcampus.fastcatch.common.utility.exception;

import kr.co.fastcampus.fastcatch.common.exception.BaseException;

public class DateIsLaterThanCurrentException extends BaseException {

    public DateIsLaterThanCurrentException() {
        super(AvailableUtilErrorCode.DATE_IS_LATER_THAN_CURRENT.getErrorMsg());
    }

}
