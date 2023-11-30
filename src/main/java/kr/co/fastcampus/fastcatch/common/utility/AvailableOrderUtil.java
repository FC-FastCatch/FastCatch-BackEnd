package kr.co.fastcampus.fastcatch.common.utility;

import java.time.LocalDate;
import kr.co.fastcampus.fastcatch.common.exception.InvalidDateRangeException;
import kr.co.fastcampus.fastcatch.common.exception.InvalidHeadRangeException;
import kr.co.fastcampus.fastcatch.common.exception.PastDateException;

public class AvailableOrderUtil {
    public static void validateDate(LocalDate startDate, LocalDate endDate) {
        if (startDate.isBefore(LocalDate.now())) {
            throw new PastDateException();
        }
        if (endDate.isBefore(startDate)) {
            throw new InvalidDateRangeException();
        }
    }

    public static void validateHeadCount(int requestHeadCount, int roomBaseHeadCount,
                                         int roomMaxHeadcount) {
        if (requestHeadCount < roomBaseHeadCount || requestHeadCount > roomMaxHeadcount) {
            throw new InvalidHeadRangeException();
        }
    }
}
