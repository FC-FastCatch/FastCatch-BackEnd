package kr.co.fastcampus.fastcatch.common.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import kr.co.fastcampus.fastcatch.common.exception.InvalidDateRangeException;
import kr.co.fastcampus.fastcatch.common.exception.PastDateException;
import org.junit.jupiter.api.DisplayName;

class AvailableOrderUtilTest {

    //    @Test
    @DisplayName("시작 날짜가 현재 날짜 보다 과거면 에러 나는지 확인")
    void dateIsLaterThanCurrent_error() {
        LocalDate startD = LocalDate.of(1923, 11, 22);
        LocalDate endD = LocalDate.of(2023, 11, 24);

        assertThrows(PastDateException.class,
            () -> {
                AvailableOrderUtil.validateDate(startD, endD);
            }
        );

        PastDateException exception = assertThrows(
            PastDateException.class,
            () -> AvailableOrderUtil.validateDate(startD, endD)
        );

        assertEquals("현재 날짜 보다 과거의 날짜는 예약 할 수 없습니다.",
            exception.getMessage()
        );

    }

    //    @Test
    @DisplayName("시작 날짜가 종료 날짜 보다 늦으면 에러 나는지 확인")
    void startDateIsLaterThanEndDate_error() {
        LocalDate startD = LocalDate.of(2023, 11, 22);
        LocalDate endD = LocalDate.of(2023, 10, 22);

        InvalidDateRangeException exception = assertThrows(
            InvalidDateRangeException.class,
            () -> AvailableOrderUtil.validateDate(startD, endD)
        );

        assertEquals("시작 날짜가 종료 날짜 보다 늦습니다.",
            exception.getMessage()
        );

    }
}