package kr.co.fastcampus.fastcatch.common.utility.exception;

import kr.co.fastcampus.fastcatch.common.exception.BaseException;
import kr.co.fastcampus.fastcatch.common.response.ErrorCode;

public class DateIsLaterThanCurrentException extends BaseException {

    public DateIsLaterThanCurrentException() {
        super(ErrorCode.DATE_IS_LATER_THAN_CURRENT.getErrorMsg());
    }

}
