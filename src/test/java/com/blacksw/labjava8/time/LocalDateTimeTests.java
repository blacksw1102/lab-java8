package com.blacksw.labjava8.time;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class LocalDateTimeTests {

    @Test
    void LocalDateTime_초기화_테스트() {
        // given
        LocalDateTime dateTime = LocalDateTime.of(2024, Month.DECEMBER, 6, 14, 30);

        // when
        int year = dateTime.getYear();
        int month = dateTime.getMonthValue();
        int day = dateTime.getDayOfMonth();
        int hour = dateTime.getHour();
        int minute = dateTime.getMinute();

        // then
        assertEquals(2024, year);
        assertEquals(12, month);
        assertEquals(6, day);
        assertEquals(14, hour);
        assertEquals(30, minute);
    }

    @Test
    void LocalDateTime_비교_테스트() {
        // given
        LocalDateTime dateTime1 = LocalDateTime.of(2024, Month.DECEMBER, 6, 14, 30);
        LocalDateTime dateTime2 = LocalDateTime.of(2025, Month.DECEMBER, 6, 14, 30);

        // when
        boolean isBefore = dateTime1.isBefore(dateTime2);
        boolean isAfter = dateTime2.isAfter(dateTime1);

        // then
        assertTrue(isBefore);
        assertTrue(isAfter);
    }

}
