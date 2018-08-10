package com.zhangjikai.tool;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Jikai Zhang on 2017/4/16.
 */
public class CountDownLatchTest {


    public static void main(String[] args) throws InterruptedException {
        int threadNums = 10;
        final CountDownLatch countDownLatch = new CountDownLatch(10);
        Thread threads[] = new Thread[threadNums];
        for(int i = 0; i < threadNums; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    countDownLatch.countDown();
                    System.out.println(Thread.currentThread().getName());

                }
            });
            threads[i].start();
        }
        countDownLatch.await();
        System.out.println("main thread");


    }
}
