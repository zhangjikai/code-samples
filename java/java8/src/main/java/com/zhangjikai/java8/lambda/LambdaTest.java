package com.zhangjikai.java8.lambda;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Jikai Zhang
 * @date 2017/12/4.
 */


@FunctionalInterface
interface Larger<T> {
    int large(T t, T t2);
}

@FunctionalInterface
interface ThrowExceptionInterface {
    void run(int a, int b) throws IOException;
}

@FunctionalInterface
interface VoidInterface {
    void run(int a);
}

public class LambdaTest {
    
    public static void main(String[] args) {
        Integer a = 10;
        Integer b = 11;
        run(a, b, (t, t2) -> t.compareTo(t2));
        throwException();
    }
    
    public static <T> void run(T t, T t2, Larger<T> larger) {
        System.out.println(larger.large(t, t2));
    }
    
    public void demo() {
        Calculator c = (int a, int b) -> a + b;
        
        Calculator c2 = new Calculator() {
            @Override
            public int cal(int a, int b) {
                return a + b;
            }
        };
    }
    
    public static void throwException() {
        ThrowExceptionInterface t = (int a, int b) -> {
            throw new IOException();
        };
    }
    
    public static void voidTest() {
        List<String> list = new ArrayList<>();
        VoidInterface v = a -> a++;
    }
    
    
    //Even better
    public static void filter(List<String> names, Predicate<String> condition) {
        names.stream().filter((name) -> (condition.test(name)))
                .forEach((name) -> {
                            System.out.println(name + " ");
                        }
                );
    }
    
    
}

