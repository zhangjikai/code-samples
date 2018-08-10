package com.zhangjikai.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * Created by Jikai Zhang on 2017/4/17.
 */
public class AtomicIntegerFieldUpdaterTest {

    static class Custom {
        volatile int value;

        public Custom(int value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {
        AtomicIntegerFieldUpdater<Custom> updater = AtomicIntegerFieldUpdater.newUpdater(Custom.class, "value");

        Custom custom = new Custom(10);

        System.out.println(updater.getAndIncrement(custom));
        System.out.println(custom.value);
    }
}
