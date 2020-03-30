package com.zhangjikai.hppc;

import static com.zhangjikai.hppc.HPPCTest.COUNT;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;

import org.junit.Test;

import it.unimi.dsi.fastutil.ints.Int2LongArrayMap;
import it.unimi.dsi.fastutil.ints.Int2LongMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import objectexplorer.MemoryMeasurer;

/**
 * @author Jikai Zhang
 * @date 2020-03-28
 */
public class FastUtilTest {
    
    @Test
    public void testFastUtil() {
        IntArrayList integers = new IntArrayList();
        integers.add(1);
        integers.forEach((IntConsumer) System.out::println);
        integers.forEach((int i) -> {
            System.out.println(i + 1);
        });
        integers.stream().map(i -> i + 1).collect(Collectors.toList());
        
        Int2LongMap map = new Int2LongArrayMap();
        map.put(1, 2);
        map.forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });
        
    }
    
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        long time = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            // list.add(1000);
        }
        System.out.printf("cost: %sms\n", System.currentTimeMillis() - time);
        System.out.printf("size: %sM\n", 1.0 * MemoryMeasurer.measureBytes(list) / 1000_000);
        time = System.currentTimeMillis();
        IntArrayList intList = new IntArrayList();
        for (int i = 0; i < COUNT; i++) {
            intList.add(1000);
        }
        System.out.printf("fast cost: %sms\n", System.currentTimeMillis() - time);
        System.out.printf("fast size: %sM\n", 1.0 * MemoryMeasurer.measureBytes(intList) / 1000_000);
    }
    
}
