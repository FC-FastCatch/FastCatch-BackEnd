package kr.co.fastcampus.fastcatch.common.utility;

import java.time.LocalDate;
import kr.co.fastcampus.fastcatch.common.exception.InvalidDateRangeException;
import kr.co.fastcampus.fastcatch.common.exception.InvalidHeadRangeException;
import kr.co.fastcampus.fastcatch.common.exception.PastDateException;

public class AvailableOrderUtil {
    public static void validateDate(LocalDate start, LocalDate end) {
        if (start.isBefore(LocalDate.now())) {
            throw new PastDateException();
        }
        if (end.isBefore(start)) {
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
