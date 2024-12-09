package com.blacksw.labjava8.thread;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ThreadTests {

    // Runnable은 작업 결과를 외부로 리턴하지 않는다.
    @Test
    void Thread_runnable_테스트() throws Exception {
        // given
        AtomicBoolean isExecuted = new AtomicBoolean(false);
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // when
        Runnable task = () -> isExecuted.set(true);
        executorService.submit(task);
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        // then
        assertTrue(isExecuted.get());
    }

    // Runnable 인터페이스는 내부에서 발생한 예외를 밖으로 전파하지 못한다.
    // 때문에 예외가 발생한다면 메서드 내부에서 처리해야 한다.
    @Test
    void Thread_runnable_예외처리_테스트() throws InterruptedException {
        Runnable task = () -> {
            try {
                // when
                throw new RuntimeException("exception..!");
            } catch (RuntimeException e) {
                // then
                assertEquals("exception..!", e.getMessage());
            }
        };
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(task);
        executorService.shutdown();;
        executorService.awaitTermination(1, TimeUnit.MINUTES);
    }

    // Callable은 메서드 수행 결과를 리턴가능하고, 그 결과를 Future를 통해서 받을 수 있다.
    @Test
    void Thread_callable_테스트() throws ExecutionException, InterruptedException {
        // given
        Callable<Boolean> task = () -> {
            boolean result = false;
            return result;
        };
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Boolean> future = executorService.submit(task);

        // when
        Boolean result = future.get();
        executorService.shutdown();

        // then
        assertFalse(result);
    }

    // Callable은 내부 메서드에서 발생한 예외를 외부로 전파가 가능하다.
    @Test
    void Thread_callable_예외처리_테스트() {
        // given
        Callable<Void> task = () -> {
            throw new RuntimeException("exception..!");
        };
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Void> future = executorService.submit(task);

        // when
        String exceptionMessage = "";
        try {
            future.get();
        } catch (Exception e) {
            exceptionMessage = e.getCause().getMessage();
        } finally {
            executorService.shutdown();
        }

        // then
        assertEquals("exception..!", exceptionMessage);
    }

    // 멀티쓰레드에서 작업이 처리되는 순서는 ExecutorService에서 submit한 순서와는 다를 수 있다.
    @Test
    void Thread_멀티스레드_runnable_테스트() throws InterruptedException {
        // given
        List<String> executionOrder = Collections.synchronizedList(new ArrayList<>());
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        // when
        for (int i = 0; i < 1000; i++) {
            String value = "Task" + i;
            executorService.submit(() -> {
               executionOrder.add(value);
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        // then
        assertEquals(1000, executionOrder.size());
    }

    @Test
    void Thread_멀티스레드_callable_테스트() throws InterruptedException, ExecutionException {
        // given
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        List<Callable<Integer>> tasks = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            int value = i;
            tasks.add(() -> value);
        }

        // when
        List<Future<Integer>> results = executorService.invokeAll(tasks);
        executorService.shutdown();
        int sum = 0 ;
        for (Future<Integer> result : results) {
            sum += result.get();
        }

        // then
        assertEquals(500500, sum);
    }

    // Executor의 invokeAny는 처리해야할 Task들 중 가장 먼저 작업이 끝난 Task의 결과 값만을 취급한다.
    @Test
    void Thread_멀티스레드_invokeAny_테스트() throws ExecutionException, InterruptedException {
        // given
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        List<Callable<Integer>> tasks = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            int value = i * 100;
            tasks.add(() -> {
                Thread.sleep(value);
                return value;
            });
        }

        // when
        Integer result = executorService.invokeAny(tasks);
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        // then
        assertEquals(100, result);
    }

}
