package com.blacksw.labjava8.functionalInterface;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FunctionalInterfaceTests {

    @Test
    void Fi_Supplier_테스트() {
        // given
        Supplier<Integer> supplier = () -> new Random().nextInt(1000);

        // when
        int randomValue = supplier.get();

        // then
        assertInstanceOf(Integer.class, randomValue);
    }

    @Test
    void Fi_Consumer_테스트() {
        // given
        List<String> list = new ArrayList<>();
        Consumer<String> consumer = list::add;

        // when
        consumer.accept("value1");
        consumer.accept("value2");

        // then
        assertEquals("value1", list.get(0));
        assertEquals("value2", list.get(1));
    }

    @Test
    void Fi_Predicate_테스트() {
        // given
        Predicate<Integer> predicate = (value) -> value % 2 == 1;

        // when
        boolean result1 = predicate.test(1);
        boolean result2 = predicate.test(2);

        // then
        assertTrue(result1);
        assertFalse(result2);
    }

    @Test
    void Fi_Function_테스트() {
        // given
        Function<Integer, String> function = (value) -> String.format("%,d", value);

        // when
        String result1 = function.apply(999);
        String result2 = function.apply(1000);
        String result3 = function.apply(9999);
        String result4 = function.apply(10000);

        // then
        assertEquals("999", result1);
        assertEquals("1,000", result2);
        assertEquals("9,999", result3);
        assertEquals("10,000", result4);
    }

    @Test
    void Fi_BiFunction_테스트() {
        // given
        BiFunction<Integer, Integer, Integer> function = Integer::sum;

        // when
        int result = function.apply(2, 3);

        // then
        assertEquals(5, result);
    }

}
