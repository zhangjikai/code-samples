package com.zhangjikai.unsafe;

import org.junit.Test;
import sun.misc.Unsafe;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

/**
 * Created by Jikai Zhang on 2017/4/8.
 * <p>
 * 直接操作 native 内存，内存不会被回收，需要手动释放。使用 natvie 内存可以分配元素数目大于 Integer.MAX_VALUE 的数组。在 Java 中，创建
 * 数组时，数组大小是一个 int 值，所以不能创建 size 大于 Integer.MAX_VALUE 的数组。
 */
public class DirectInArray {

    private final static long INT_SIZE_IN_BYTES = 4;
    private final long startIndex;
    private Unsafe unsafe;

    public static Unsafe getUnsafe() {

        Unsafe unsafe = null;
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);


        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return unsafe;
    }

    public DirectInArray() {
        startIndex = 0;
    }

    public DirectInArray(long size) {
        unsafe = getUnsafe();
        // 分配内存块，并返回内存块的首地址，按字节分配，一个 int 是 4 个字节。类似 c 中 malloc，内存分配完并没有初始化。
        startIndex = unsafe.allocateMemory(size * INT_SIZE_IN_BYTES);
        // 初始化内存块。从 startIndex 开始，将 size * INT_SIZE_IN_BYTES 个字节的值设为 0， 类似于 c 中的 memset。
        unsafe.setMemory(startIndex, size * INT_SIZE_IN_BYTES, (byte) 0);
    }

    public void setValue(long index, int value) {
        unsafe.putInt(index(index), value);
    }

    public int getValue(long index) {
        return unsafe.getInt(index(index));
    }

    private long index(long offset) {
        return startIndex + offset * INT_SIZE_IN_BYTES;
    }

    public void destroy() {
        // 释放内存，类似于 c 的中 free.
        unsafe.freeMemory(startIndex);
    }

    /**
     * 计算 JVM 中对象的大小
     *
     * @param clazz
     * @return
     */
    public long sizeOf(Class<?> clazz) {

        long maximumOffset = 0;
        do {
            for (Field f : clazz.getDeclaredFields()) {
                if (!Modifier.isStatic(f.getModifiers())) {
                    maximumOffset = Math.max(maximumOffset, unsafe.objectFieldOffset(f));
                }
            }
        }

        while ((clazz = clazz.getSuperclass()) != null);
        return maximumOffset + 8;
    }

    public void testMallaciousAllocation() throws Exception {
        long address = unsafe.allocateMemory(2L * 4);
        unsafe.setMemory(address, 8L, (byte) 0);
        assertEquals(0, unsafe.getInt(address));
        assertEquals(0, unsafe.getInt(address + 4));
        unsafe.putInt(address + 1, 0xffffffff);
        assertEquals(0xffffff00, unsafe.getInt(address));
        assertEquals(0x000000ff, unsafe.getInt(address + 4));
    }

    public static void main(String[] args) throws Exception {
        long size = 100;
        DirectInArray directInArray = new DirectInArray(size);
        directInArray.setValue(0L, 10);
        directInArray.setValue(size - 1, 20);
        assertEquals(10, directInArray.getValue(0L));
        assertEquals(20, directInArray.getValue(size - 1));
        directInArray.destroy();

        directInArray.testMallaciousAllocation();

    }
}
