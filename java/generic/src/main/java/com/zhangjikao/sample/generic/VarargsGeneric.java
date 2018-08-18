package com.zhangjikao.sample.generic;

/**
 * @author Jikai Zhang
 * @date 2018-08-16
 */
public class VarargsGeneric {
    
    public static void main(String[] args) {
        print(1, 3, 4);
    }
    @SafeVarargs
    private static <T> void print(T... args) {
        for(T t : args) {
            System.out.println(t);
        }
    }
}
