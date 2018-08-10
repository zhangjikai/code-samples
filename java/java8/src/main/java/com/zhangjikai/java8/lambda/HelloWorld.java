package com.zhangjikai.java8.lambda;

/**
 *
 * @author Jikai Zhang
 * @date 2017/12/4.
 */
public class HelloWorld {
    public static void main(String[] args) {
        Calculator c = (a, b) -> a + b;
        System.out.println(c.cal(1, 2));
        c = (a, b) -> a * b;
        System.out.println(c.cal(1, 2));

        c = (a, b) -> a;
        System.out.println(c.cal(1, 2));
        c = (a, b) -> 0;
        System.out.println(c.cal(1, 2));
    }
}

@FunctionalInterface
interface Calculator {
    int cal(int a, int b);
}
