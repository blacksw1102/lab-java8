package com.blacksw.labjava8.optional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OptionalTests {

    // Optional.of()에 null 값을 대입했을 경우 NullPointerException 발생 여부를 확인한다.
    @Test
    void Optional_of_null_테스트() {
        // when
        Exception exception = null;
        try {
            Optional.of(null);
        } catch (NullPointerException e) {
            exception = e;
        }

        // then
        assertInstanceOf(NullPointerException.class, exception);
    }

    @Test
    void Optional_ofNullable_null_테스트() {
        // when
        Optional<String> optional = Optional.ofNullable(null);

        // then
        assertThat(optional).isEmpty();
    }

    // 비어있지 않는 Optional일 경우 isPresent는 true 값을 리턴한다.
    // 비어있지 않는 Optional에서는 get을 하면 원문 값을 리턴한다.
    @Test
    void Optional_안비어있는_Optional_처리_테스트() {
        // when
        Optional<String> stringOptional = Optional.of("string value");

        // then
        assertTrue(stringOptional.isPresent());
        assertEquals("string value", stringOptional.get());
    }

    // 빈 Optional일 경우 isPresent는 false를 리턴한다.
    // 빈 optional에서 orElse를 호출할 경우 default 값을 리턴받는다.
    // Optional.orElseGet을 호출하면 빈 Optional의 경우 파라미터로 넘겨받은 Supplier가 제공해주는 값을 리턴한다.
    // Optional.orElseThrow를 호출하면 빈 Optional의 경우 Supplier를 통해 Exception을 리턴받는다.
    @Test
    void Optional_비어있는_Optional_처리_테스트() {
        // when
        Optional<String> emptyOptional = Optional.ofNullable(null);

        // then
        assertFalse(emptyOptional.isPresent());
        assertEquals("default value", emptyOptional.orElse("default value"));
        assertEquals("supply value", emptyOptional.orElseGet(() -> "supply value"));
        Exception e = assertThrows(NullPointerException.class, () -> emptyOptional.orElseThrow(() -> new NullPointerException("null exception")));
        assertEquals("null exception", e.getMessage());
    }

    // Optional.map 그리고 Optional.flatMap 모두 기본적으로 리턴할 값을 Optional로 래핑해서 리턴을 제공한다.
    // Optional의 map과 flatMap 차이는 파라미터로 넘기는 Function의 리턴값이 Optional로 래핑되어있는지 유무에 해당한다.
    // Optional.map은 Function 내부에서 리턴하는 값이 이미 Optional로 감싸져 있음으로써 발생하는
    // 이중 Optional 구조를 지원하지 않는다 때문에 Function은 원문 값만을 리턴해야만 하도록 되어있다.
    // Optional.flatMap의 경우 Function 내부에서 리턴하는 값이 이미 Optional로 래핑되어 있다면
    // 리턴할 값이 이중 Optional 구조가 이루어지기 때문에 이룰 평탄화처리해서 하나의 Optional로만 이루어지도록 구성한다.
    @Test
    void Optional_map_flatMap_비교() {
        // given
        Optional<String> optional = Optional.of("value");

        // when
        Optional<String> mapResult = optional.map(String::toUpperCase);
        Optional<String> flatMapResult = optional.flatMap((value) -> Optional.of(value.toUpperCase()));

        // then
        assertEquals("VALUE", mapResult.get());
        assertEquals("VALUE", flatMapResult.get());
    }

    // Optional.filter는 Optional이 포함하고 있는 값에 대해서 조건부 필터링을 지원한다.
    // Optional이 포함하고 있는 값이 Predicate의 조건을 충족하지 못한다면 Optional.filter는 빈 Optional을 리턴한다.
    // Optional이 포함하고 있는 값이 Predicate의 조건을 충족하면 원래 가지고 있던 값(this)을 그대로 리턴 받는다.
    @Test
    void Optional_filter_테스트() {
        // given
        Optional<String> optional = Optional.of("value");

        // when
        Optional<String> filterResult2 = optional.filter((value) -> value.startsWith("a"));
        Optional<String> filterResult1 = optional.filter((value) -> value.startsWith("v"));

        // then
        assertFalse(filterResult2.isPresent());
        assertTrue(filterResult1.isPresent());
    }

    // Optional에 포함된 값이 null이더라도 중간 연산 과정에서 수행이 잘못되면 orElse를 통해서 default 값을 리턴 받을 수 있다.
    @Test
    void Optional_NullSafe_테스트() {
        // given
        Optional<String> optional = Optional.ofNullable(null);

        // when
        String result = optional
                .map(String::toUpperCase)
                .filter((value) -> value.contains("value"))
                .orElse("default value");

        // then
        assertEquals("default value", result);
    }

    // Optional이 비어있지 않는 경우에만 Consumer을 통해서 특정 작업을 수행한다.
    @Test
    void Optional_ifPresent() {
        // given
        Optional<String> presentOptional = Optional.ofNullable("123456");

        // when
        presentOptional.ifPresent((value) -> System.out.println("Optional value is " + value));
    }

}
