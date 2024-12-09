package com.blacksw.labjava8.collection.queue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

// ConcurrentLinkedQueue 주요 기능
// - Concurrency
// - 메모리 제한 없음
// - Output non blocking
@SpringBootTest
public class ConcurrentLinkedQueueTests {

    // ConcurrentLinkedQueue 이용 시 동시성 처리가 보장 되는지 확인한다.
    @Test
    void CL_concurrency_테스트() throws InterruptedException {
        // given
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();

        // when
        List<Runnable> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(() -> {
                for (int j = 0; j < 10; j++) {
                    queue.add(j);
                }
            });
        };
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (Runnable runnable : list) {
            executorService.submit(runnable);
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        // then
        assertEquals(100, list.size());
        assertEquals(1000, queue.size());
    }

    // ConcurrentLinkedQueue에 아이템 적재 시, capacity 제한이 있는지 확인한다.
    @Test
    void CL_메모리제한없음_테스트() {
        // given
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        int itemCount = 1_000_000;

        // when
        for (int i = 0; i < itemCount; i++) {
            queue.add(i);
        }

        // then
        assertEquals(itemCount, queue.size());
    }

    // ConcurrentLinkedQueue에서 아이템을 추출 할 때, non-blocking 방식인지 확인한다.
    @Test
    void CL_output_non_blocking_테스트() {
        // given
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();

        // when
        int nullCount = 0;
        for (int i = 0; i < 10; i++) {
            if (queue.poll() == null) {
                nullCount++;
            }
        }

        // then
        assertEquals(10, nullCount);
    }

}
