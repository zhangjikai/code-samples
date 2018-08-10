package com.zhangjikai.atomic;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Created by Jikai Zhang on 2017/4/17.
 */
public class AtomicIntegerArrayTest {

    public static Unsafe getUnsafe() {

        Unsafe unsafe = null;
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);


        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return unsafe;
    }


    public static void main(String[] args) {
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(10);
        atomicIntegerArray.getAndSet(0, 10);
        System.out.println(atomicIntegerArray.get(0));

        Unsafe unsafe = getUnsafe();
        int scale = unsafe.arrayIndexScale(int[].class);
        if ((scale & (scale - 1)) != 0)
            throw new Error("data type scale not a power of two");
        int shift = 31 - Integer.numberOfLeadingZeros(scale);
        System.out.println(scale + " " + shift);

        System.out.println(Integer.numberOfLeadingZeros(1));
        System.out.println(Integer.numberOfLeadingZeros(2));
        System.out.println(Integer.numberOfLeadingZeros(4));
        System.out.println(Integer.numberOfLeadingZeros(8));
        System.out.println(Integer.numberOfLeadingZeros(16));

    }
}
