package com.blacksw.labjava8.time;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class LocalDateTests {

    @Test
    void LocalDate_초기화_테스트() {
        // given
        LocalDate date = LocalDate.of(2024, Month.DECEMBER, 6);

        // when
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        String str = date.toString();

        // then
        assertEquals(2024, year);
        assertEquals(12, month);
        assertEquals(6, day);
        assertEquals("2024-12-06", str);
    }

    @Test
    void LocalDate_plus_minus_테스트() {
        // given
        LocalDate localDate = LocalDate.of(2024, Month.DECEMBER, 6);

        // when
        LocalDate plusLocalDate = localDate.plusMonths(1);
        LocalDate minusLocalDate = localDate.minusMonths(1);
        int yearPlusResult = plusLocalDate.getYear();
        int monthPlusResult = plusLocalDate.getMonthValue();
        int dayPlusResult = plusLocalDate.getDayOfMonth();
        int yearMinusResult = minusLocalDate.getYear();
        int monthMinusResult = minusLocalDate.getMonthValue();
        int dayMinusResult = minusLocalDate.getDayOfMonth();

        // then
        assertEquals(2025, yearPlusResult);
        assertEquals(1, monthPlusResult);
        assertEquals(6, dayPlusResult);
        assertEquals(2024, yearMinusResult);
        assertEquals(11, monthMinusResult);
        assertEquals(6, dayMinusResult);
    }

    @Test
    void LocalDate_비교_테스트() {
        // given
        LocalDate localDate1 = LocalDate.of(2024, Month.DECEMBER, 6);
        LocalDate localDate2 = LocalDate.of(2024, Month.DECEMBER, 5);

        // when
        boolean result1 = localDate1.isAfter(localDate2);
        boolean result2 = localDate2.isBefore(localDate1);

        // then
        assertTrue(result1);
        assertTrue(result2);
    }

}
