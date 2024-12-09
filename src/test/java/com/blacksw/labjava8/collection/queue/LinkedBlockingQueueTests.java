package com.blacksw.labjava8.collection.queue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

// LinkedBlockingQueue 주요 기능
// - Concurrency
// - Input & Output Blocking
// - 메모리 제한 unbounded
@SpringBootTest
public class LinkedBlockingQueueTests {

    // LinkedBlockingQueue 동시성 처리 여부 확인한다.
    @Test
    void LBQ_concurrency_테스트() throws InterruptedException {
        // given
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

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

    // LinkedBlockingQueue는 capacity가 다 찬 상태에서 아이템 삽입을 시도할 경우, 공간이 날 때까지 대기하는지 확인한다.
    // LinkedBlockingQueue는 빈 상태에서 아이템 추출을 시도할 경우, 아이템이 들어올 때 까지 대기하는지 확인한다.
    @Test
    void LBQ_input_output_blocking_테스트() throws InterruptedException {
        // given
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(2);

        // when
        Thread producer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    queue.put(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread consumer = new Thread(() -> {
           for (int i = 0; i < 5; i++) {
               try {
                   queue.take();
                   Thread.sleep(2000);
               } catch (InterruptedException e) {
                   Thread.currentThread().interrupt();
               }
           }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();

        // then
        assertEquals(0, queue.size());
    }

    // LinkedBlockingQueue는 capacity가 제한되어 있고 다 찼을 때, 아이템 삽입을 시도하면 unbounded 처리를 하는지 확인한다.
    @Test
    void LBQ_메모리_제한_unbounded_테스트() throws InterruptedException {
        // given
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(2);

        // when
        for (int i = 0; i < 5; i++) {
            queue.offer(i, 1, TimeUnit.SECONDS);
        }

        // then
        List<Integer> list = Arrays.asList(0, 1);
        for (Integer value : list) {
            assertEquals(value, queue.take());
        }
    }

}
