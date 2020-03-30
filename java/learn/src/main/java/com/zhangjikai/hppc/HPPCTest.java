package com.zhangjikai.hppc;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.carrotsearch.hppc.IntArrayList;
import com.carrotsearch.hppc.IntLongHashMap;
import com.carrotsearch.hppc.IntLongMap;
import com.carrotsearch.hppc.procedures.IntLongProcedure;
import com.carrotsearch.hppc.procedures.IntProcedure;

import objectexplorer.MemoryMeasurer;

/**
 * @author Jikai Zhang
 * @date 2020-03-29
 */
public class HPPCTest {
    
    static final int COUNT = 100_000_000;
    @Test
    public void testHppc() {
        IntArrayList intList = new IntArrayList();
        intList.add(1);
        intList.forEach((IntProcedure) System.out::println);
        IntLongMap intLongMap = new IntLongHashMap();
        intLongMap.put(1, 2);
        intLongMap.forEach((IntLongProcedure) (k, v) -> {
           System.out.println(k + ":" + v);
        });
    }
    
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        long time = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            // list.add(1000);
        }
        System.out.printf("jdk  cost: %sms\n", System.currentTimeMillis() - time);
        System.out.printf("jdk  size: %sM\n", 1.0 * MemoryMeasurer.measureBytes(list) / 1000_000);
        time = System.currentTimeMillis();
        IntArrayList intList = new IntArrayList();
        for (int i = 0; i < COUNT; i++) {
            intList.add(1000);
        }
        System.out.printf("hppc cost: %sms\n", System.currentTimeMillis() - time);
        System.out.printf("hppc size: %sM\n", 1.0 * MemoryMeasurer.measureBytes(intList) / 1000_000);
    }
}
