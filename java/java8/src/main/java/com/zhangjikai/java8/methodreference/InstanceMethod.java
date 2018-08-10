package com.zhangjikai.java8.methodreference;

import org.junit.Test;

import java.util.function.Function;

class Person {

    private String name;

    public Person() {
        name = "default";
    }

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public String mergeName(Person p) {
        return name + " " + p.name;
    }
}

/**
 *
 * @author Jikai Zhang
 * @date 2017/12/8.
 */
public class InstanceMethod {

    @Test
    public void run() {
        Person a = new Person("aa");
        Person b = new Person("bb");

        int i = 10;

        Function<Person, String> f = Person::getName;
        String r = f.apply(b);
        System.out.println(r);

        Function<Person, String> f2 = a::mergeName;
        r = f2.apply(b);
        System.out.println(r);

    }
}


