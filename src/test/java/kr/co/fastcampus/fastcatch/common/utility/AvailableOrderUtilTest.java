package kr.co.fastcampus.fastcatch.common.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import kr.co.fastcampus.fastcatch.common.utility.exception.DateIsLaterThanCurrentException;
import kr.co.fastcampus.fastcatch.common.utility.exception.StartDateIsLaterThanEndDateException;
import org.junit.jupiter.api.Test;

class AvailableOrderUtilTest {

    @Test
    void 시작날짜가_현재날짜_보다_과거면_에러나는지_확인() {
        LocalDate startD = LocalDate.of(1923, 11, 22);
        LocalDate endD = LocalDate.of(2023, 11, 24);

        DateIsLaterThanCurrentException exception = assertThrows(
            DateIsLaterThanCurrentException.class,
            () -> AvailableOrderUtil.validateDate(startD, endD)
        );

        assertEquals("현재 날짜 보다 과거의 날짜는 예약 할 수 없습니다.",
            exception.getMessage()
        );

    }

    @Test
    void 시작날짜가_종료날짜_보다_늦으면_에러나는지_확인() {
        LocalDate startD = LocalDate.of(2023, 11, 22);
        LocalDate endD = LocalDate.of(2023, 10, 22);

        StartDateIsLaterThanEndDateException exception = assertThrows(
            StartDateIsLaterThanEndDateException.class,
            () -> AvailableOrderUtil.validateDate(startD, endD)
        );

        assertEquals("시작 날짜가 종료 날짜 보다 늦습니다.",
            exception.getMessage()
        );

    }
}