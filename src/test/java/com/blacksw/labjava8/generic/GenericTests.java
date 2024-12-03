package com.blacksw.labjava8.generic;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class GenericTests {

    @Test
    void Generic_컴파일과정_타입안정성체크_테스트() {
        // given
        List<String> list = new ArrayList<>();

        // when
        list.add("value1");

        // then
        assertEquals("value1", list.get(0));;
    }

    @Test
    void Generic_코드재사용_테스트() {
        // given
        Box<Integer> intBox = new Box<>();
        Box<String> stringBox = new Box<>();

        // when
        intBox.setValue(123);
        stringBox.setValue("123");

        // then
        assertEquals(123, intBox.getValue());
        assertEquals("123", stringBox.getValue());
    }

    @Test
    void Generic_상한바운드_테스트() {
        // given
        NumberBox<Integer> intBox = new NumberBox<>();
        NumberBox<Long> longBox = new NumberBox<>();
        NumberBox<Double> doubleBox = new NumberBox<>();

        // when
        intBox.setValue(123);
        longBox.setValue(123L);
        doubleBox.setValue(123.0);

        // then
        assertEquals(123, intBox.getValue());
        assertEquals(123L, longBox.getValue());
        assertEquals(123.0, doubleBox.getValue());
    }

    @Test
    void Generic_제네릭메서드_테스트() {
        // given
        Integer[] intArr = new Integer[] {1, 2, 3, 4};
        String[] stringArr = new String[] {"red", "blue", "green", "yellow"};

        // when
        GenericUtils.swap(intArr, 0, 2);
        GenericUtils.swap(stringArr, 0, 2);

        // then
        assertArrayEquals(new Integer[]{3, 2 ,1, 4}, intArr);
        assertArrayEquals(new String[]{"green", "blue", "red", "yellow"}, stringArr);
    }

}
