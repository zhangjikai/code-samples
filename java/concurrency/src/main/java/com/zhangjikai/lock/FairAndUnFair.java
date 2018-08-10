package com.zhangjikai.lock;

import com.zhangjikai.basic.ThreadDemo;
import org.junit.Test;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Jikai Zhang on 2017/4/16.
 * <p>
 * 公平锁和非公平锁测试
 * <p>
 * 非公平锁是 ReentrantLock 的默认实现，因为非公平锁可以减少线程切换的次数，
 * 增加程序执行效率，但是非公平锁有可能造成饥饿
 */
public class FairAndUnfair {

    private static ReentrantLock2 fairLock = new ReentrantLock2(true);
    private static ReentrantLock2 unfairLock = new ReentrantLock2(false);

    static class ReentrantLock2 extends ReentrantLock {
        public ReentrantLock2(boolean fair) {
            super(fair);
        }


        /*public Collection<Thread> getQueuedThreads() {
            List<Thread> threadList = new ArrayList<>(super.getQueuedThreads());
            Collections.reverse(threadList);

            return threadList;
        }*/

        public List<Long> getThreadIds() {
            List<Long> threadIds = new ArrayList<>();
            List<Thread> threadList = new ArrayList<>(getQueuedThreads());
            Collections.reverse(threadList);
            for (Thread thread : threadList) {
                threadIds.add(thread.getId());
            }
            return threadIds;
        }
    }

    private static class Job extends Thread {
        private ReentrantLock2 lock;

        public Job(ReentrantLock2 lock) {
            this.lock = lock;
        }

        @Override
        public void run() {

            lock.lock();
            try {
                Thread.sleep(100);
                System.out.println("current-thread: " + Thread.currentThread().getId() + " " + "waiting threads:" + lock.getThreadIds());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

            lock.lock();
            try {
                Thread.sleep(100);
                System.out.println("current-thread: " + Thread.currentThread().getId() + " " + "waiting threads:" + lock.getThreadIds());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }


    private void testLock(ReentrantLock2 lock) throws InterruptedException {
        int threadNum = 5;
        Thread threads[] = new Thread[threadNum];
        for (int i = 0; i < threadNum; i++) {
            threads[i] = new Job(lock);
        }

        for (int i = 0; i < threadNum; i++) {
            threads[i].start();
        }

        for (int i = 0; i < threadNum; i++) {
            threads[i].join();
        }
    }

    @Test
    public void testFair() throws InterruptedException {
        System.out.println("Fair Lock");
        testLock(fairLock);
    }

    @Test
    public void testUnfair() throws InterruptedException {
        System.out.println("Unfair Lock");
        testLock(unfairLock);
    }

}
