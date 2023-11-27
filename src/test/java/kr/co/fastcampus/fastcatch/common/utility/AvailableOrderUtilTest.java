package kr.co.fastcampus.fastcatch.common.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import kr.co.fastcampus.fastcatch.common.exception.DateIsLaterThanCurrentException;
import kr.co.fastcampus.fastcatch.common.exception.StartDateIsLaterThanEndDateException;
import org.junit.jupiter.api.DisplayName;

class AvailableOrderUtilTest {

    //    @Test
    @DisplayName("시작 날짜가 현재 날짜 보다 과거면 에러 나는지 확인")
    void dateIsLaterThanCurrent_error() {
        LocalDate startD = LocalDate.of(1923, 11, 22);
        LocalDate endD = LocalDate.of(2023, 11, 24);

        assertThrows(DateIsLaterThanCurrentException.class,
            () -> {
                AvailableOrderUtil.validateDate(startD, endD);
            }
        );

        DateIsLaterThanCurrentException exception = assertThrows(
            DateIsLaterThanCurrentException.class,
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

        StartDateIsLaterThanEndDateException exception = assertThrows(
            StartDateIsLaterThanEndDateException.class,
            () -> AvailableOrderUtil.validateDate(startD, endD)
        );

        assertEquals("시작 날짜가 종료 날짜 보다 늦습니다.",
            exception.getMessage()
        );

    }
}