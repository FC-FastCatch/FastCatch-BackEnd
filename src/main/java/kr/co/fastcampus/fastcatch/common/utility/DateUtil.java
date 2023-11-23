package kr.co.fastcampus.fastcatch.common.utility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import kr.co.fastcampus.fastcatch.common.exception.handler.InvalidDateFormatException;

public class DateUtil {
    /**
     * String 타입을 LocalDateTime 타입으로 변환
     *
     * @param dateTimeString String 타입의 날짜
     * @return LocalDateTime LocalDataeTime 타입의 날짜
     */
    public static LocalDateTime stringToDateTime(String dateTimeString) {
        try {
            String pattern = "yyyy-MM-dd HH:mm";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return LocalDateTime.parse(dateTimeString, formatter);
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException("올바르지 않은 형식의 날짜입니다.(yyyy-MM-dd HH:mm)");
        }
    }

    /**
     * String 타입을 LocalDate 타입으로 변환
     *
     * @param dateString String 타입의 날짜
     * @return LocalDate LocalDate 타입의 날짜
     */
    public static LocalDate stringToDate(String dateString) {
        try {
            String pattern = "yyyy-MM-dd";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            return LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException("올바르지 않은 형식의 날짜입니다.(yyyy-MM-dd)");
        }
    }

    /**
     * LocalDate 타입을 String 타입으로 변환
     *
     * @param date LocalDate 타입의 날짜
     * @return String 타입의 날짜
     */
    public static String localDateToString(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /**
     * LocalDateTime 타입을 String 타입으로 변환
     *
     * @param date LocalDateTime 타입의 날짜
     * @return String 타입의 날짜
     */
    public static String localDateTimeToString(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
