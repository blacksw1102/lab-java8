package com.blacksw.labjava8.optional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OptionalTests {

    @Test
    void Optional_of_ofNullable_비교_테스트() {

        // then - value가 null일 경우, NullPointException이 발생하는지 확인
        assertThrows(NullPointerException.class, () -> Optional.of(null));

        // then - value가 null일 경우 비어있는 Optional을 리턴하는지 확인
        Optional<String> optional = Optional.ofNullable(null);
        assertThat(optional).isEmpty();
    }

    @Test
    void Optional_안비어있는_Optional_처리_테스트() {
        Optional<String> stringOptional = Optional.of("string value");

        // 비어있지 않는 Optional일 경우 isPresent는 true 값을 리턴한다.
        assertTrue(stringOptional.isPresent());

        // 비어있지 않는 Optional에서는 get을 하면 원문 값을 리턴한다.
        assertEquals("string value", stringOptional.get());
    }

    @Test
    void Optional_비어있는_Optional_처리_테스트() {
        Optional<String> emptyOptional = Optional.ofNullable(null);

        // 빈 Optional일 경우 isPresent는 false를 리턴한다.
        assertFalse(emptyOptional.isPresent());

        // 빈 optional에서 orElse를 호출할 경우 default 값을 리턴받는다.
        assertEquals("default value", emptyOptional.orElse("default value"));

        // Optional.orElseGet을 호출하면 빈 Optional의 경우 파라미터로 넘겨받은 Supplier가 제공해주는 값을 리턴한다.
        assertEquals("supply value", emptyOptional.orElseGet(() -> "supply value"));

        // Optional.orElseThrow를 호출하면 빈 Optional의 경우 Supplier를 통해 Exception을 리턴받는다.
        Exception e = assertThrows(NullPointerException.class, () -> emptyOptional.orElseThrow(() -> new NullPointerException("null exception")));
        assertEquals("null exception", e.getMessage());
    }

    @Test
    void Optional_map_flatMap_비교() {
        Optional<String> optional = Optional.of("value");

        // Optional.map 그리고 Optional.flatMap 모두 기본적으로 리턴할 값을 Optional로 래핑해서 리턴을 제공한다.
        // Optional의 map과 flatMap 차이는 파라미터로 넘기는 Function의 리턴값이 Optional로 래핑되어있는지 유무에 해당한다.

        // Optional.map은 Function 내부에서 리턴하는 값이 이미 Optional로 감싸져 있음으로써 발생하는
        // 이중 Optional 구조를 지원하지 않는다 때문에 Function은 원문 값만을 리턴해야만 하도록 되어있다.
        Optional<String> mapResult = optional.map(String::toUpperCase);
        assertEquals("VALUE", mapResult.get());

        // Optional.flatMap의 경우 Function 내부에서 리턴하는 값이 이미 Optional로 래핑되어 있다면
        // 리턴할 값이 이중 Optional 구조가 이루어지기 때문에 이룰 평탄화처리해서 하나의 Optional로만 이루어지도록 구성한다.
        Optional<String> flatMapResult = optional.flatMap((value) -> Optional.of(value.toUpperCase()));
        assertEquals("VALUE", flatMapResult.get());
    }

    @Test
    void Optional_filter_테스트() {
        Optional<String> optional = Optional.of("value");

        // Optional.filter는 Optional이 포함하고 있는 값에 대해서 조건부 필터링을 지원한다.

        // Optional이 포함하고 있는 값이 Predicate의 조건을 충족하지 못한다면 Optional.filter는 빈 Optional을 리턴한다.
        Optional<String> filterResult2 = optional.filter((value) -> value.startsWith("a"));
        assertFalse(filterResult2.isPresent());

        // Optional이 포함하고 있는 값이 Predicate의 조건을 충족하면 원래 가지고 있던 값(this)을 그대로 리턴 받는다.
        Optional<String> filterResult1 = optional.filter((value) -> value.startsWith("v"));
        assertTrue(filterResult1.isPresent());
    }

    @Test
    void Optional_NullSafe처리_테스트() {
        Optional<String> optional = Optional.ofNullable(null);

        // Optional에 포함된 값이 null이더라도 중간 연산 과정에서 수행이 잘못되면 orElse를 통해서 default 값을 리턴 받을 수 있다.
        String result = optional
                .map(String::toUpperCase)
                .filter((value) -> value.contains("value"))
                .orElse("default value");

        assertEquals("default value", result);
    }

    @Test
    void Optional_ifPresent() {
        Optional<String> presentOptional = Optional.ofNullable("123456");

        // Optional이 비어있지 않는 경우에만 Consumer을 통해서 특정 작업을 수행한다.
        presentOptional.ifPresent((value) -> System.out.println("Optional value is " + value));
    }

}
