package com.zhangjikai.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * @author Jikai Zhang
 * @date 2018-08-27
 */
public class StopThread {
    private static volatile boolean stop;
    
    public static void main(String[] args) throws InterruptedException {
        Thread backThread = new Thread(() -> {
            int i = 0;
            while (!stop) {
                i++;
            }
        });
        
        backThread.start();
        TimeUnit.SECONDS.sleep(1);
        stop = true;
        
        
    }
}
