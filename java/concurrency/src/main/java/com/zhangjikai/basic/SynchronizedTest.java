package com.zhangjikai.basic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Jikai Zhang on 2017/5/2.
 */
public class SynchronizedTest {

    public static int counter = 0;

    public synchronized static void increase() {
        for(int i = 0; i < 10000; i++) {
            counter++;
        }
    }


    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(100);
        for(int i = 0; i < 10; i ++) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    increase();
                }
            });
        }
        executor.shutdown();
        while (!executor.isTerminated()) {

        }
        System.out.println(counter);
    }

}
