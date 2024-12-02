package com.blacksw.labjava8.collection.map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class LinkedHashMapTests {

    @Test
    void LinkedHashMap_삽입순서_유지_테스트() {
        // given
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("aaa", null);
        map.put("bbb", null);
        map.put("ccc", null);
        map.put("ddd", null);
        map.put("eee", null);

        // when
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, Object>> entrySet = map.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            sb.append(entry.getKey()).append(" ");
        }

        // then
        assertEquals("aaa bbb ccc ddd eee", sb.toString().trim());
    }

    @Test
    void LinkedHashMap_null_키_값_가능_테스트() {
        // given
        Map<String, Object> map = new LinkedHashMap<>();

        // when
        map.put(null, null);
        map.put("123", "123");

        // then
        assertEquals(map.get(null), null);
        assertEquals(map.get("123"), "123");
    }

    @Test
    void LinkedHashMap_accessOrder_테스트() {
        // given
        LinkedHashMap<String, String> map = new LinkedHashMap<>(16, 0.75f, true);
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");

        // when
        map.get("key1");
        map.get("key2");

        // then
        List<String> keys = new ArrayList<>(map.keySet());
        assertEquals(Arrays.asList("key3", "key1", "key2"), keys);
    }

}
