package com.zhangjikai.samples;

/**
 * @author Jikai Zhang
 * @date 2019-06-23
 */
public class DeadLock {
    
    private static Object lock = new Object();
    private static Object lock2 = new Object();
    
    
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            synchronized (lock) {
                System.out.println(1);
                synchronized (lock2) {
                    System.out.println(2);
                }
            }
            
        });
        thread.setName("Test-thread-1");
        thread.start();
        synchronized (lock2) {
            System.out.println(3);
            synchronized (lock) {
                System.out.println(4);
            }
        }
    }
}
