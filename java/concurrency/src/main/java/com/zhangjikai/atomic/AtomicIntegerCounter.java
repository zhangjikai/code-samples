package com.zhangjikai.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Jikai Zhang on 2017/4/17.
 */
public class AtomicIntegerCounter {
    private static AtomicInteger counter = new AtomicInteger(0);
    private static int intCounter = 0;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        int threadNums = 10;

        for (int i = 0; i < threadNums; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 1000000; i++) {
                        counter.addAndGet(2);
                        intCounter += 2;
                    }
                }
            };
            executorService.execute(runnable);
        }


        executorService.shutdown();
        try {
            while (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(counter);
        System.out.println(intCounter);
    }

}
