package com.blacksw.labjava8.collection.queue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayDeque;

import static org.junit.jupiter.api.Assertions.assertEquals;

// ArrayDequeue 주요 기능
// - 양방향 add & remove
// - like stack
// - like queue
@SpringBootTest
public class ArrayDequeTests {

    // ArrayDeque에 양방향으로 아이템을 적재하고 추출이 되는지 확인한다.
    @Test
    void AD_양방향_add_remove_테스트() {
        // given
        ArrayDeque<Integer> deque = new ArrayDeque<>();

        // when
        deque.addFirst(1);
        deque.addLast(5);
        int lastValue = deque.removeLast();
        int firstValue = deque.removeFirst();

        // then
        assertEquals(1, firstValue);
        assertEquals(5, lastValue);
    }

    // ArrayDeque를 Stack과 같이 활용 가능한지 확인한다.
    @Test
    void AD_like_stack_테스트() {
        // given
        ArrayDeque<Integer> deque = new ArrayDeque<>();

        // when
        deque.push(1);
        deque.push(5);

        // then
        assertEquals(5, deque.peek());
        assertEquals(5, deque.pop());
        assertEquals(1, deque.peek());
        assertEquals(1, deque.pop());
    }

    // ArrayDeque를 Queue와 같이 활용 가능한지 확인한다.
    @Test
    void AD_like_queue_테스트() {
        // given
        ArrayDeque<Integer> deque = new ArrayDeque<>();

        // when
        deque.offer(1);
        deque.offer(5);

        // then
        assertEquals(1, deque.peekFirst());
        assertEquals(1, deque.poll());
        assertEquals(5, deque.peekFirst());
        assertEquals(5, deque.poll());
    }

}
