package kr.co.fastcampus.fastcatch.common.utility;

import java.time.LocalDate;
import kr.co.fastcampus.fastcatch.common.utility.exception.DateIsLaterThanCurrentException;
import kr.co.fastcampus.fastcatch.common.utility.exception.StartDateIsLaterThanEndDateException;

public class AvailableOrderUtil {
    public static void validateDate(LocalDate start, LocalDate end) {
        if (start.isBefore(LocalDate.now())) {
            throw new DateIsLaterThanCurrentException();
        }
        //시작이 현재 보다 늦고, 종료가 현재 보다 과거면 여기에 걸리게 돼 있음
        if (!start.isEqual(end) && start.isAfter(end)) {
            throw new StartDateIsLaterThanEndDateException();
        }
    }

}
