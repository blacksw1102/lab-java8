package com.blacksw.labjava8.time;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ZonedDateTimeTests {

    @Test
    void ZonedDateTime_초기화_테스트() {
        // when
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2024, 12, 6, 14, 30, 0, 0, ZoneId.of("America/New_York"));

        // then
        assertEquals(2024, zonedDateTime.getYear());
        assertEquals(12, zonedDateTime.getMonthValue());
        assertEquals(6, zonedDateTime.getDayOfMonth());
        assertEquals(14, zonedDateTime.getHour());
        assertEquals(30, zonedDateTime.getMinute());
        assertEquals("America/New_York", zonedDateTime.getZone().getId());
    }

    @Test
    void ZonedDateTime_시간대_변경_테스트() {
        // given
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2024, 12, 6, 13, 30, 0, 0, ZoneId.of("Asia/Seoul"));

        // when
        ZonedDateTime newYorkDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("America/New_York"));

        // then
        // 뉴욕시는 서울시보다 14시간 느리다.
        assertEquals(2024, newYorkDateTime.getYear());
        assertEquals(12, newYorkDateTime.getMonthValue());
        assertEquals(5, newYorkDateTime.getDayOfMonth());
        assertEquals(23, newYorkDateTime.getHour());
        assertEquals(30, newYorkDateTime.getMinute());
        assertEquals("America/New_York", newYorkDateTime.getZone().getId());
    }

}
