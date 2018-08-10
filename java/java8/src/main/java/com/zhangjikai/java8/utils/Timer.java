package com.zhangjikai.java8.utils;

/**
 * @author Jikai Zhang
 * @date 2018-05-01
 */
public class Timer {
    
    private static ThreadLocal<Long> startTimeLocal = new ThreadLocal<>();
    
    public static void start() {
        long startTime = System.currentTimeMillis();
        startTimeLocal.set(startTime);
    }
    
    public static void end() {
        long endTime = System.currentTimeMillis();
        long usedTime = endTime - startTimeLocal.get();
        System.out.println("运行时间：" + usedTime + "ms");
        startTimeLocal.remove();
    }
    
    public static void end(boolean removeStart) {
        long endTime = System.currentTimeMillis();
        long usedTime = endTime - startTimeLocal.get();
        System.out.println("运行时间：" + usedTime + "ms");
        if (removeStart) {
            startTimeLocal.remove();
        }
    }
}
