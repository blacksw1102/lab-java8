package com.blacksw.labjava8.collection.map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ConcurrentHashMapTests {

    @Test
    void ConcurrentHashMap_동시성_삽입_테스트() throws InterruptedException {
        // given
        Map<String, String> map = new ConcurrentHashMap<>();
        int count = 10000;

        // when
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        for (int i = 0; i < count; i++) {
            String key = "key" + i;
            String value = "value" + i;
            executorService.execute(() -> {
                map.put(key, value);
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        // then
        assertEquals(count, map.size());
    }

    @Test
    void ConcurrentHashMap_동시성_삭제_테스트() throws InterruptedException {
        // given
        Map<String, String> map = new ConcurrentHashMap<>();
        int size = 10000;
        for (int i = 0; i < size; i++) {
            map.put("key" + i, "value" + i);
        }

        // when
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        for (int i = 0; i < size; i++) {
            String key = "key" + i;
            executorService.execute(() -> {
                map.remove(key);
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        // then
        assertEquals(0, map.size());
    }

    @Test
    void ConcurrentHashMap_null_키_값_불가능_테스트() {
        Map<String, String> map = new ConcurrentHashMap<>();
        assertThrows(NullPointerException.class, () -> map.put(null, "vlaue"));
        assertThrows(NullPointerException.class, () -> map.put("key", null));
    }

}
