package com.zhangjikai.lock;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by Jikai Zhang on 2017/4/8.
 */
public class CASIntTest {
    private volatile int count = 0;

    private static final Unsafe unsafe = getUnsafe();
    private static final long offset;

    // 获得 count 属性在 CASIntTest 中的偏移量（内存地址偏移）
    static {
        try {
            offset = unsafe.objectFieldOffset(CASIntTest.class.getDeclaredField("count"));
        } catch (NoSuchFieldException e) {
            throw new Error(e);
        }
    }
    // 通过反射的方式获得 Unsafe 类
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


    public void increment() {
        int previous = count;
        unsafe.compareAndSwapInt(this, offset, previous, previous + 1);
    }

    public static void main(String[] args) {
        CASIntTest casIntTest = new CASIntTest();
        casIntTest.increment();
        System.out.println(casIntTest.count);
    }
}
