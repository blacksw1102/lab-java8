package com.blacksw.labjava8.collection.map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TreeMapTests {

    @Test
    void TreeMap_키정렬_오름차순_테스트() {
        // given
        Map<Integer, String> orders = new TreeMap<>();
        orders.put(1003, "Order-1003");
        orders.put(1001, "Order-1001");
        orders.put(1004, "Order-1004");
        orders.put(1002, "Order-1002");
        orders.put(1005, "Order-1005");

        // when
        Integer[] sortedKeys = orders.keySet().toArray(new Integer[0]);

        // then
        assertThat(sortedKeys).containsExactly(1001, 1002, 1003, 1004, 1005);
    }

    @Test
    void TreeMap_키정렬_내림차순_테스트() {
        // given
        Map<Integer, String> orders = new TreeMap<>(Comparator.reverseOrder());
        orders.put(1003, "Order-1003");
        orders.put(1001, "Order-1001");
        orders.put(1004, "Order-1004");
        orders.put(1002, "Order-1002");
        orders.put(1005, "Order-1005");

        // when
        Integer[] sortedKeys = orders.keySet().toArray(new Integer[0]);

        //then
        assertThat(sortedKeys).containsExactly(1005, 1004, 1003, 1002, 1001);
    }

    @Test
    void TreeMap_null키_불가능_테스트() {
        // given
        TreeMap<String, Integer> treeMap = new TreeMap<>();

        // when & then
        assertThrows(NullPointerException.class, () -> {
            treeMap.put(null, 123);
        });
    }

    @Test
    void TreeMap_범위_탐색_테스트() {
        // given
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        LocalDate currentDate = startDate;

        TreeMap<String, Integer> treeMap = new TreeMap<>();
        while (!currentDate.isAfter(endDate)) {
            treeMap.put(currentDate.format(dateFormat), 0);
            currentDate = currentDate.plusDays(1);
        }

        // when
        NavigableMap<String, Integer> rangedMap = treeMap.subMap("2024-10-01", true, "2024-10-30", true);

        // that
        assertEquals(30, rangedMap.size());
        assertEquals("2024-10-01", rangedMap.firstKey());
        assertEquals("2024-10-30", rangedMap.lastKey());
    }

    @Test
    void TreeMap_범위_제거_테스트() {
        // given
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        for (int i = 10; i >= 1; i--) {
            treeMap.put(i, 0);
        }

        // when
        treeMap.subMap(1, true, 3, true).clear();

        // that
        assertEquals(7, treeMap.size());
        assertEquals(4, treeMap.firstKey());
        assertEquals(10, treeMap.lastKey());
    }

    @Test
    void TreeMap_순회_탐색_테스트() {
        // given
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        for (int i = 3; i >= 1; i--) {
            treeMap.put(i, i);
        }

        // when
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, Integer> entry : treeMap.entrySet()) {
            sb.append(entry.getKey()).append("-").append(entry.getValue()).append(" ");
        }

        // then
        assertEquals("1-1 2-2 3-3", sb.toString().trim());
    }

}
