package com.zhangjikai.tool;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by Jikai Zhang on 2017/4/16.
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        int threadNums = 10;
        final CyclicBarrier barrier = new CyclicBarrier(threadNums);
        Thread threads[] = new Thread[threadNums];
        for(int i = 0; i < threadNums; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(100);
                        barrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }

                    System.out.println(Thread.currentThread().getName());

                }
            });
            threads[i].start();
        }
    }
}
