package com.zhangjikai.tool;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by Jikai Zhang on 2017/4/16.
 */
public class SemaphoreTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(5);
        for(int i = 0; i < 50; i++) {
            final int index = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        System.out.println("Accessing: " + index);

                        Thread.sleep((long) (Math.random() * 1000));
                        semaphore.release();
                        System.out.println("------------------" + semaphore.availablePermits());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            executorService.execute(runnable);
        }

        executorService.shutdown();
    }

}
