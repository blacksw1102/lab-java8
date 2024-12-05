package com.blacksw.labjava8.methodReference;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MethodReferenceTests {

    @Test
    void MR_staticMethod_테스트() {
        // given
        List<String> list = Arrays.asList("1", "2", "3");

        // when
        List<Integer> result = list.stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        // then
        assertEquals(Arrays.asList(1, 2, 3), result);
    }

    @Test
    void MR_instanceMethod_테스트() {
        // given
        List<String> list = Arrays.asList("a", "b", "c");

        // when
        List<String> result = list.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        // then
        assertEquals(Arrays.asList("A", "B", "C"), result);
    }

    @Test
    void MR_constructorMethod_테스트() {
        // given
        Optional<String> name = Optional.of("test name");

        // when
        Optional<Person> person = name.map(Person::new);

        // then
        assertEquals("test name, 0", person.get().toString());
    }

}
