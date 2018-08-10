package com.zhangjikai.collection;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by Jikai Zhang on 2017/4/17.
 * <p>
 * 使用阻塞队列实现生产者消费者模式
 */
public class ProducerConsumer {

    static class Storage {
        private static final int MAX_COUNT = 5;
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(MAX_COUNT);

        public void put(Integer item) {
            try {

                System.out.println("produce product: " + item);
                queue.put(item);
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public Integer take() {
            Integer item = 0;
            try {

                item = queue.take();
                System.out.println("consume product: " + item);
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
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


    public static void main(String[] args) throws InterruptedException {
        Storage storage = new Storage();
        Thread consumerThread = new Thread(new ConsumerThread(storage));
        Thread producerThread = new Thread(new ProducerThread(storage));
        consumerThread.start();
        producerThread.start();
        consumerThread.join();
        producerThread.join();
    }
}
