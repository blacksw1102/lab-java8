package com.blacksw.labjava8.stream;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class StreamTests {

    @Test
    void Stream_지연평가_테스트() {
        // given
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        AtomicInteger atomicInteger = new AtomicInteger();

        // when
        Stream<Integer> stream = numbers.stream().map((n) -> {
            atomicInteger.incrementAndGet();
            return n * 2;
        });
        int result1 = atomicInteger.get();

        stream.collect(Collectors.toList());
        int result2 = atomicInteger.get();

        // then
        assertEquals(0, result1);
        assertEquals(numbers.size(), result2);
    }

    @Test
    void Stream_filter_테스트() {
        // given
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        // when
        List<Integer> result = numbers.stream()
                .filter((n) -> n % 2 == 1)
                .collect(Collectors.toList());

        // then
        assertEquals(Arrays.asList(1, 3, 5), result);
    }

    @Test
    void Stream_map_테스트() {
        // given
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        // when
        List<Integer> result = numbers.stream()
                .map(n -> n * 2)
                .collect(Collectors.toList());

        // then
        assertEquals(Arrays.asList(2, 4, 6, 8, 10), result);
    }

    @Test
    void Stream_flatMap_테스트() {
        // given
        List<String> list = Arrays.asList(
                "apple, banana, cherry",
                "apple, banana",
                "apple");

        // when
        List<String> result = list.stream()
                .flatMap(s -> Stream.of(s.split(",")))
                .map(String::trim)
                .sorted()
                .collect(Collectors.toList());

        // then
        assertEquals(Arrays.asList("apple", "apple", "apple", "banana", "banana", "cherry"), result);
    }

    @Test
    void Stream_집계_테스트() {
        // given
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        // when
        int min = numbers.stream().min(Comparator.comparing(a -> a)).get();
        int max = numbers.stream().max(Comparator.comparing(a -> a)).get();
        long count = numbers.stream().filter(a -> a % 2 == 1).count();
        int sum = numbers.stream().reduce(0, Integer::sum);
        int product = numbers.stream().reduce(1, (a, b) -> a * b);

        // then
        assertEquals(1, min);
        assertEquals(5, max);
        assertEquals(3, count);
        assertEquals(15, sum);
        assertEquals(120, product);
    }

}
