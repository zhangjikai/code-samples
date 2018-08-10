package com.zhangjikai.lock;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by Jikai Zhang on 2017/4/8.
 */
public class CASCounter {

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

    private volatile long counter = 0;
    private static final long offset;
    private static final Unsafe unsafe = getUnsafe();

    static {
        try {
            offset = unsafe.objectFieldOffset(CASCounter.class.getDeclaredField("counter"));
        } catch (NoSuchFieldException e) {
            throw new Error(e);
        }
    }

    public void increment() {
        long before = counter;
        while (!unsafe.compareAndSwapLong(this, offset, before, before + 1)) {
            before = counter;
        }
    }

    public long getCounter() {
        return counter;
    }

    private static long intCounter = 0;

    public static void main(String[] args) throws InterruptedException {
        int threadCount = 10;
        Thread threads[] = new Thread[threadCount];
        final CASCounter casCounter = new CASCounter();

        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {

                    for (int i = 0; i < 10000; i++) {
                        casCounter.increment();
                        intCounter++;
                    }
                }
            });
            threads[i].start();
        }

        for(int i = 0; i < threadCount; i++) {
            threads[i].join();
        }
        System.out.printf("CASCounter is %d \nintCounter is %d\n", casCounter.getCounter(), intCounter);
    }
}
