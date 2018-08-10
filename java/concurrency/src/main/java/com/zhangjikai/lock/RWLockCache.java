package com.zhangjikai.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Jikai Zhang on 2017/4/16.
 * <p>
 * 使用读写锁
 */
public class RWLockCache {
    static Map<String, Object> map = new HashMap<>();
    static ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    static Lock readLock = rwLock.readLock();
    static Lock writeLock = rwLock.writeLock();

    public static final Object get(String key) {
        readLock.lock();
        try {
            return map.get(key);
        } finally {
            readLock.unlock();
        }
    }

    public static final Object put(String key, Object value) {
        writeLock.lock();
        try {
            return map.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    public static final void clear() {
        writeLock.lock();
        try {
            map.clear();
        } finally {
            writeLock.unlock();
        }
    }
}
