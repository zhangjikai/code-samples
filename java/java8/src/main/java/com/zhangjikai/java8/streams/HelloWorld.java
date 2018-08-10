package com.zhangjikai.java8.streams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Jikai Zhang
 * @date 2017/12/10.
 */
public class HelloWorld {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(5);
        list.add(10);
        list.add(1);
        list.add(-1);
        list.add(1000);
        list.add(100);

        List<Integer> result = list.stream()
            .filter(i -> i > 10)
            .sorted()
            .collect(Collectors.toList());

        result.forEach(System.out::println);



    }
}
