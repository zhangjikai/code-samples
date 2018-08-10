package com.zhangjikai.collection;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * Created by Jikai Zhang on 2017/4/16.
 */
public class ForkJoinTest {

    static class CounterTask extends RecursiveTask<Integer> {


        private static final int THRESHOLD = 2;
        private int start;
        private int end;

        public CounterTask(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {

            int sum = 0;
            boolean canCompute = (end - start) <= THRESHOLD;

            if (canCompute) {
                for (int i = start; i <= end; i++) {
                    sum += i;
                }
            } else {
                int mid = (start + end) / 2;
                CounterTask leftTask = new CounterTask(start, mid);
                CounterTask rightTask = new CounterTask(mid + 1, end);
                leftTask.fork();
                rightTask.fork();
                int leftResult = leftTask.join();
                int rightResult = rightTask.join();
                sum = leftResult + rightResult;
            }
            return sum;
        }

    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CounterTask task = new CounterTask(1, 30);
        Future<Integer> result = forkJoinPool.submit(task);
        System.out.println(result.get());
    }

}
