package kr.co.fastcampus.fastcatch.common.utility;

import java.time.LocalDate;
import kr.co.fastcampus.fastcatch.common.utility.exception.StartDateIsLaterThanEndDateException;

public class AvailableOrderUtil {
    public static void validateDate(LocalDate start, LocalDate end) {
        if (!start.isEqual(end) && start.isAfter(end)) {
            throw new StartDateIsLaterThanEndDateException();
        }
    }

}
