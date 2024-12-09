package com.blacksw.labjava8.collection.queue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.PriorityQueue;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;

// PriorityList 주요 기능
// - Comparator Insert
@SpringBootTest
public class PriorityQueueTests {

    // 큐에 아이템을 무작위 순서로 적재 후, 추출 시 아이템의 우선순위 순으로 나오는지를 확인한다.
    @Test
    void PQ_compartor_적재_테스트() {
        // given
        Queue<Integer> queue = new PriorityQueue<>();

        // when
        queue.add(3);
        queue.add(1);
        queue.add(5);
        queue.add(4);
        queue.add(2);

        // then
        assertEquals(1, queue.poll());
        assertEquals(2, queue.poll());
        assertEquals(3, queue.poll());
        assertEquals(4, queue.poll());
        assertEquals(5, queue.poll());
    }

}
