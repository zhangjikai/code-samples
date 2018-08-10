package com.zhangjikai.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Jikai Zhang on 2017/4/4.
 */
public class ReentrantLockTest {
    private static int count = 1;
    private static Lock lock = new ReentrantLock();

    static class CustomThread implements Runnable {


        @Override
        public void run() {

            lock.lock();
            try {
                count++;
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().toString() + ": " + count);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new CustomThread());
        Thread thread2 = new Thread(new CustomThread());
        thread.start();
        thread2.start();
    }

}
