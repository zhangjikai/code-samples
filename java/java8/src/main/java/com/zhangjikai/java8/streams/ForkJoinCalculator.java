package com.zhangjikai.java8.streams;

import com.zhangjikai.java8.utils.Timer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * 使用 ForkJoin 框架示例
 *
 * @author Jikai Zhang
 * @date 2018-05-01
 */
public class ForkJoinCalculator extends RecursiveTask<Long> {
    
    private final long[] numbers;
    private final int start;
    private final int end;
    
    public static final long THRESHOLD = 10_000;
    
    public ForkJoinCalculator(long[] numbers) {
        this(numbers, 0, numbers.length);
    }
    
    public ForkJoinCalculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }
    
    @Override
    protected Long compute() {
        int length = end - start;
        if (length <= THRESHOLD) {
            return computeSequentially();
        }
        
        ForkJoinCalculator leftTask = new ForkJoinCalculator(numbers, start, start + length / 2);
        leftTask.fork();
        ForkJoinCalculator rightTask = new ForkJoinCalculator(numbers, start + length / 2, end);
        Long rightResult = rightTask.compute();
        Long leftResult = leftTask.join();
        
        return leftResult + rightResult;
        
    }
    
    private long computeSequentially() {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }
        return sum;
    }
    
    public static void main(String[] args) {
        int n = 10_000_000;
        Timer.start();
        long[] numbers = LongStream.rangeClosed(1, n).toArray();
        ForkJoinTask<Long> task = new ForkJoinCalculator(numbers);
        long result = new ForkJoinPool().invoke(task);
        System.out.println(result);
        Timer.end();
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
