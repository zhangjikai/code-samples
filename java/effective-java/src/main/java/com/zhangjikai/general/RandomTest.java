package com.zhangjikai.general;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Jikai Zhang
 * @date 2018-08-25
 */
public class RandomTest {
    static Random random = new Random();
    
    static int randomInt(int n) {
        return Math.abs(random.nextInt()) %n;
    }
    
    public static void main(String[] args) {
        int n = 2 * (Integer.MAX_VALUE / 3);
        int low = 0;
        for (int i = 0; i < 1000000; i++) {
            if (randomInt(n) < n / 2) {
                low++;
            }
        }
        System.out.println(low);
        System.out.println(Math.abs(Integer.MIN_VALUE));
        System.out.println(ThreadLocalRandom.current().nextInt());
    }
}
