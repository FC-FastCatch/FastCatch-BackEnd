package kr.co.fastcampus.fastcatch.common.utility;

import java.time.LocalDate;
import kr.co.fastcampus.fastcatch.common.exception.InvaildDateRangeException;
import kr.co.fastcampus.fastcatch.common.exception.InvaildHeadRangeException;
import kr.co.fastcampus.fastcatch.common.exception.PastDateException;

public class AvailableOrderUtil {
    public static void validateDate(LocalDate start, LocalDate end) {
        if (start.isBefore(LocalDate.now())) {
            throw new PastDateException();
        }
        //시작이 현재 보다 늦고, 종료가 현재 보다 과거면 여기에 걸리게 돼 있음
        if (!start.isEqual(end) && start.isAfter(end)) {
            throw new InvaildDateRangeException();
        }
    }

    public static void validateHeadCount(int requestHead, int roomBaseHead, int roomMaxHead) {
        if (requestHead < roomBaseHead || requestHead > roomMaxHead) {
            throw new InvaildHeadRangeException();
        }
    }

}
