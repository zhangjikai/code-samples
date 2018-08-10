package com.zhangjikai.java8.feature;

import java.util.Optional;

/**
 * @author Jikai Zhang
 * @date 2018-05-01
 */
public class OptionalDemo {
    private static void test(String s) {
        Optional<String> string = Optional.ofNullable(s);
        string.ifPresent(t -> System.out.println(111));
    }
    
    public static void main(String[] args) {
        String s = "222";
        test(s);
        
    }
}
