package com.blacksw.labjava8.methodReference;

public class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person(String name) {
        this(name, 0);
    }

    @Override
    public String toString() {
        return String.format("%s, %d", name, age);
    }
}
