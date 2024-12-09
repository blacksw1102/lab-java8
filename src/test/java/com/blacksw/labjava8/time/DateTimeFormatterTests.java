package com.blacksw.labjava8.time;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DateTimeFormatterTests {

    @Test
    void DTF_FromDateTimeToString_변환_테스트() {
        // given
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 6, 14, 30, 0, 0);

        // when
        String formattedDateTime = dateTime.format(formatter);

        // then
        assertEquals("2024-12-06 14:30:00", formattedDateTime);
    }

    @Test
    void DTF_FromStringToDateTime_변환_테스트() {
        // given
        String formattedDateTime = "2024-12-06 14:30:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // when
        LocalDateTime dateTime = LocalDateTime.parse(formattedDateTime, formatter);

        // then
        assertEquals(2024, dateTime.getYear());
        assertEquals(12, dateTime.getMonthValue());
        assertEquals(6, dateTime.getDayOfMonth());
        assertEquals(14, dateTime.getHour());
        assertEquals(30, dateTime.getMinute());
    }

}
