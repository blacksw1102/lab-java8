package com.blacksw.labjava8.generic;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WildcardTests {

    @Test
    void Wildcard_상한경계_테스트() {
        // 상한 경계("? extends")는 읽기만 가능하고 쓰기는 불가능하다.
        // when
        List<? extends Number> list = Arrays.asList(123, 123L, 123.123f, 123.123456);

        // then
        assertEquals(123, list.get(0));
        assertEquals(123.123456, list.get(3));
    }

    @Test
    void Wildcard_하한경계_테스트() {
        // given
        List<? super Integer> list = Arrays.asList(1, 2, 3);

        // when
        // Arrays.asList로 생성한 리스트는 불변성의 띈다.
        list= new ArrayList<>(list);
        list.add(4);
        list.add(5);
        Object first = list.get(0);

        // then
        assertInstanceOf(Integer.class, first);
    }

    @Test
    void Wildcard_무제한_테스트() {
        // when
        List<?> list = Arrays.asList("value", 'C', 1, 12L, 12.3f, 12.345, null);

        // then
        assertInstanceOf(String.class, list.get(0));
        assertInstanceOf(Character.class, list.get(1));
        assertInstanceOf(Integer.class, list.get(2));
        assertInstanceOf(Long.class, list.get(3));
        assertInstanceOf(Float.class, list.get(4));
        assertInstanceOf(Double.class, list.get(5));
        assertNull(list.get(6));
    }

    @Test
    void Wildcard_타입캐스팅_테스트() {
        // given
        List<?> list = Arrays.asList("value1", "value2", "value3");

        // when
        List<String> stringList = (List<String>) list;

        // then
        assertEquals("value1", stringList.get(0));
        assertThrows(ClassCastException.class, () -> {
            List<Integer> integerList = (List<Integer>) list;
            for (Integer value : integerList) {
                System.out.println(value);
            }
        });
    }

    @Test
    void Wildcard_와일드카드메서드_테스트() {
        // given
        List<Integer> integerList = Arrays.asList(1, 2, 3);
        List<Double> doubleList = Arrays.asList(1.0, 2.0, 3.0);

        // when
        String toStringResult1 = GenericUtils.toString(integerList);
        String toStringResult2 = GenericUtils.toString(doubleList);

        // then
        assertEquals("1 2 3", toStringResult1);
        assertEquals("1.0 2.0 3.0", toStringResult2);
    }

    @Test
    void Wildcard_메서드오버로딩_테스트() {
        List<Integer> intList = Arrays.asList(1, 2, 3);
        List<Double> doubleLIst = Arrays.asList(1.1, 2.2);
        List<Number> numberList = Arrays.asList(1, 2.2);

        GenericUtils.printList(intList);
        GenericUtils.printList(doubleLIst);
        GenericUtils.printList(numberList);
        GenericUtils.printList2(intList);
        GenericUtils.printList2(numberList);
    }

}
