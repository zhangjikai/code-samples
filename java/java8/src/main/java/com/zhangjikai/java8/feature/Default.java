package com.zhangjikai.java8.feature;

/**
 * @author Jikai Zhang
 * @date 2018-05-01
 */
public class Default {
    public static void main(String[] args) {
        C c = new C();
        c.print();
    }
}

interface P {
    default void print() {
        System.out.println(111);
    }
}

class  C implements P {

}