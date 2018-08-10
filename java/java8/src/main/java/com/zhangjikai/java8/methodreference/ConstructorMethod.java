package com.zhangjikai.java8.methodreference;

import org.junit.Test;

import java.applet.Applet;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 *
 * @author Jikai Zhang
 * @date 2017/12/8.
 */
public class ConstructorMethod {

    @Test
    public void run() {
        // 相当于 Supplier<Person> t = () -> new Person();
        Supplier<Person> s = Person::new;
        Person p = s.get();
        System.out.println(p.getName());

        // 相当于 Function<String, Person> t = (name) -> new Person(name);
        Function<String, Person> f = Person::new;
        p = f.apply("1234");
        System.out.println(p.getName());
    }
}
