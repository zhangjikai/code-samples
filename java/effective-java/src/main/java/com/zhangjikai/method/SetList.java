package com.zhangjikai.method;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Jikai Zhang
 * @date 2018-08-20
 */
public class SetList {
    public static void main(String[] args) {
        Set<Integer> set = new HashSet<>();
        List<Integer> list = new ArrayList<>();
        for (int i = -3; i < 3; i++) {
            set.add(i);
            list.add(i);
        }
        for (int i = 0; i < 3; i++) {
            set.remove(i);
            // list.remove(i);
            list.remove((Integer) i);
        }
        System.out.println(set + " " + list);
    }
}
