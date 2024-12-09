package com.blacksw.labjava8.collection.queue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class LinkedListTests {

    // 큐에 아이템을 적재하고, 적재 순서대로 아이템이 추출되는지를 확인한다.
    @Test
    void LL_적재_테스트() {
        // given
        Queue<Integer> queue = new LinkedList<>();

        // when
        queue.add(3);
        queue.add(1);
        queue.add(5);
        queue.add(4);
        queue.add(2);

        // then
        assertEquals(3, queue.poll());
        assertEquals(1, queue.poll());
        assertEquals(5, queue.poll());
        assertEquals(4, queue.poll());
        assertEquals(2, queue.poll());
    }

}
