package com.zhangjikai.guava;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static java.util.stream.Collectors.joining;

/**
 * @author Jikai Zhang
 * @date 2018-09-09
 */
public class Strings {
    @Test
    public void testJoiner() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4);
        System.out.println(Joiner.on(", ").join(list));
        String text = list.stream().map(String::valueOf).collect(joining(", "));
        System.out.println(text);
    }
}
