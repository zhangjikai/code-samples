package com.zhangjikai.lock;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Jikai Zhang on 2017/4/16.
 * <p>
 * 使用 Lock + Condition 实现生成者消费者模式
 */
public class ProducerConsumer {

    public static class Storage {
        private Lock lock = new ReentrantLock();
        private Condition emptyCon = lock.newCondition();
        private Condition fullCon = lock.newCondition();
        private static final int MAX_COUNT = 5;
        private Queue<Integer> queue = new LinkedList<>();

        public void put(Integer item) {
            lock.lock();
            try {
                while (queue.size() == MAX_COUNT) {
                    fullCon.await();
                }
                Thread.sleep(200);
                queue.add(item);
                System.out.println("produce product: " + item);
                emptyCon.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public Integer take() {
            Integer item = 0;
            lock.lock();
            try {
                while (queue.size() == 0) {
                    emptyCon.await();
                }
                Thread.sleep(100);
                item = queue.poll();
                System.out.println("consume product: " + item);
                fullCon.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            return item;
        }

    }

    public static class ProducerThread implements Runnable {
        private Storage storage;

        public ProducerThread(Storage storage) {
            this.storage = storage;
        }

        @Override
        public void run() {
            int num = 20;
            for (int i = 0; i < num; i++) {
                storage.put(i);
            }
        }
    }
    public static class ConsumerThread implements Runnable {

        private Storage storage;

        public ConsumerThread(Storage storage) {
            this.storage = storage;
        }

        @Override
        public void run() {
            int num = 20;
            for (int i = 0; i < num; i++) {
                storage.take();
            }
        }
    }

    public static void main(String[] args) {
        Storage storage = new Storage();
        Thread consumerThread = new Thread(new ConsumerThread(storage));
        Thread producerThread = new Thread(new ProducerThread(storage));
        consumerThread.start();
        producerThread.start();
    }

}
