package com.blacksw.labjava8.stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ParallelTests {

    // 1 - 100_000 수행시간(ms) : single < parallel
    // 1_000_000 - 10_000_000 수행시간(ms) : single > parallel
    @ParameterizedTest
    @ValueSource(ints = {1, 10, 100, 1_000, 10_000, 100_000, 1_000_000, 10_000_000})
    void Parallel_병렬처리_테스트(int value) {
        List<Integer> list = IntStream.rangeClosed(1, value).boxed().collect(Collectors.toList());

        long start1 = System.currentTimeMillis();
        long sum1 = list.stream().reduce(0, Integer::sum);
        long end1 = System.currentTimeMillis();

        long start2 = System.currentTimeMillis();
        long sum2 = list.stream().parallel().reduce(0, Integer::sum);
        long end2 = System.currentTimeMillis();

        System.out.println("single result : " + (end1 - start1));
        System.out.println("parallel result : " + (end2 - start2));
    }

    @Test
    void Parallel_동시성_테스트() {
        // given
        int size = 1_000_000;
        //List<Integer> list = new ArrayList<>();
        List<Integer> list2 = Collections.synchronizedList(new ArrayList<>());

        // when
        // 동시성이 보장되지 않는 ArrayList는 capacity가 확보되지 않은 상태에서 값을 초기화 하려다가 IndexOutBoundException을 유발할 수 있음.
        //IntStream.range(0, size).parallel().forEach(list::add);
        IntStream.range(0, size).parallel().forEach(list2::add);

        // then
        //assertNotEquals(size, list.size());
        assertEquals(size, list2.size());
    }

    @ParameterizedTest
    @CsvSource({
            "10_000_000, 2",
            "10_000_000, 5",
            "10_000_000, 10",
    })
    void Parallel_별도의ForkJoinPool사용_테스트(int value, int poolSize) {
        List<Integer> list = IntStream.range(1, value).boxed().collect(Collectors.toList());

        long start1 = System.currentTimeMillis();
        long result1 = list.parallelStream().mapToInt(i -> i).sum();
        long end1 = System.currentTimeMillis();

        ForkJoinPool forkJoinPool = new ForkJoinPool(poolSize);
        long start2 = System.currentTimeMillis();
        long result2 = forkJoinPool.submit(() -> list.parallelStream().mapToInt(i -> i).sum()).join();
        long end2 = System.currentTimeMillis();

        System.out.println("Parallel : " + (end2 - start2));
        System.out.println("ForkJoinPool : " + (end1 - start1));
    }

    @Test
    void Prallel_공용_FormJoinPool_적용_확인_테스트() {
        IntStream.range(1, 10).parallel().forEach(i -> {
            System.out.println("CurrentThread : " + Thread.currentThread().getName());
        });
    }

    // 스트림 중간연산 중 예외 발생 시, 내부에서 처리하지 않으면 최종연산이 마무리되지 않음.
    @Test
    void Parallet_예외_외부처리_테스트() {
        int result = 0;
        try {
            // when
            result = IntStream.range(1, 5)
                    .parallel()
                    .reduce(0, (a, b) -> {
                        throw new RuntimeException("exception..!");
                    });
        } catch (Exception e) {
            // then
            assertInstanceOf(RuntimeException.class, e);
            assertEquals(0, result);
        }
    }

    @Test
    void Parallel_예외_내부처리_테스트() {
        // given
        int result = 0;

        // when
        result = IntStream.range(1, 5)
                .parallel()
                .reduce(0, (a, b) -> {
                    int sum = 0;
                   try {
                       if (b == 3) {
                           throw new RuntimeException("exception..!");
                       }
                       sum = Integer.sum(a, b);
                   } catch (RuntimeException ignored) {
                   }
                   return sum;
                });

        // then
        assertEquals(7, result);
    }

}
