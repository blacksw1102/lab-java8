package com.blacksw.labjava8.collection.map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class HashMapTests {

    @Test
    void HashMap_키_순서_보장안됨_테스트() {
        // given
        Map<String, Object> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        map.put("key4", "value4");
        map.put("key5", "value5");

        // when
        List<String> keys = new ArrayList<>(map.keySet());

        // then
        assertNotEquals(Arrays.asList("key1", "key2", "key3", "key4", "key5"), keys);
    }

    @Test
    void HashMap_null_키_값_가능_테스트() {
        // given
        Map<String, Object> map = new HashMap<>();

        // when
        map.put(null, null);
        map.put("123", "123");

        // then
        assertEquals(map.get(null), null);
        assertEquals(map.get("123"), "123");
    }

    @Test
    void HashMap_중복키_값덮어쓰기_테스트() {
        // given
        Map<String, Object> map = new HashMap<>();

        // when
        map.put("key1", "value1");
        map.put("key1", "overwrite value1");

        // then
        assertEquals("overwrite value1", map.get("key1"));
    }

    @Test
    void HashMap_containsKey_containsValue_테스트() {
        // given
        Map<String, Object> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        map.put("key4", "value4");
        map.put("key5", "value5");

        assertTrue(map.containsKey("key3"));
        assertFalse(map.containsKey("key123"));
        assertTrue(map.containsValue("value4"));
        assertFalse(map.containsValue("value123"));
    }

}
