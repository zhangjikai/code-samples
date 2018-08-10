package com.zhangjikai.java8.streams;

import com.zhangjikai.java8.utils.Timer;

import java.util.stream.LongStream;

/**
 * @author Jikai Zhang
 * @date 2018-05-01
 */
public class ParallelStream {
    
    public static void parallelSum(long n) {
        long sum;
        
        // 使用 iterate 方法产生的数据不能并行，所以使用并行流更慢
        // Timer.start();
        // sum = Stream.iterate(1L, i -> i + 1).limit(n).parallel().reduce(0L, Long::sum);
        // System.out.println(sum);
        // Timer.end();
        // Timer.start();
        // sum = Stream.iterate(1L, i -> i + 1).limit(n).reduce(0L, Long::sum);
        // System.out.println(sum);
        // Timer.end();
        
        Timer.start();
        sum = LongStream.rangeClosed(1, n).parallel().reduce(0L, Long::sum);
        System.out.println(sum);
        Timer.end();
        Timer.start();
        sum = LongStream.rangeClosed(1, n).limit(n).reduce(0L, Long::sum);
        System.out.println(sum);
        Timer.end();
        Timer.start();
        sum = 0;
        for (int i = 0; i < n; i++) {
            sum += i;
        }
        System.out.println(sum);
        Timer.end();
    }
    
    
    public static void main(String[] args) {
        parallelSum(50000000);
    }
}
