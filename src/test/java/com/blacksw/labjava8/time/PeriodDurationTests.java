package com.blacksw.labjava8.time;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PeriodDurationTests {

    @Test
    void Period_초기화_테스트() {
        // given
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 6);

        // when
        Period period1 = Period.between(startDate, endDate);
        Period period2 = Period.between(endDate, startDate);

        // then
        assertEquals(11, period1.getMonths());
        assertEquals(5, period1.getDays());
        assertEquals(-11, period2.getMonths());
        assertEquals(-5, period2.getDays());
    }

    @Test
    void Duration_초기화_테스트() {
        // given
        LocalTime startTime = LocalTime.of(14, 30);
        LocalTime endTime = LocalTime.of(17, 45);

        // when
        Duration duration1 = Duration.between(startTime, endTime);
        Duration duration2 = Duration.between(endTime, startTime);

        // then
        assertEquals(3, duration1.toHours());
        assertEquals(195, duration1.toMinutes()); // 180(3분) + 15
        assertEquals(-3, duration2.toHours());
        assertEquals(-195, duration2.toMinutes()); // 180(3분) + 15
    }

}
