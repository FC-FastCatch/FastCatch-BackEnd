package kr.co.fastcampus.fastcatch.common.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import kr.co.fastcampus.fastcatch.common.utility.exception.StartDateIsLaterThanEndDateException;
import org.junit.jupiter.api.Test;

class AvailableOrderUtilTest {

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