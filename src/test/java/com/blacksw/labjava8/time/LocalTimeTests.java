package com.blacksw.labjava8.time;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class LocalTimeTests {

    @Test
    void LocalTime_초기화_테스트() {
        // when
        LocalTime localTime = LocalTime.of(14, 30);

        // then
        assertEquals(14, localTime.getHour());
        assertEquals(30, localTime.getMinute());
    }

    @Test
    void LocalTime_plus_minus_테스트() {
        // given
        LocalTime localTime = LocalTime.of(14, 30);

        // when
        LocalTime plusTime = localTime.plusMinutes(20);
        LocalTime minusTime = localTime.minusMinutes(20);

        // then
        assertEquals(14, plusTime.getHour());
        assertEquals(14, minusTime.getHour());
        assertEquals(50, plusTime.getMinute());
        assertEquals(10, minusTime.getMinute());
    }

    @Test
    void LocalTime_비교_테스트() {
        // given
        LocalTime time1 = LocalTime.of(14, 30);
        LocalTime time2 = LocalTime.of(14, 50);

        // when
        boolean isBefore = time1.isBefore(time2);
        boolean isAfter = time2.isAfter(time1);

        // then
        assertTrue(isBefore);
        assertTrue(isAfter);
    }

}
