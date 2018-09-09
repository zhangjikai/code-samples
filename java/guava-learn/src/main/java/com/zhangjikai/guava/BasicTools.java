package com.zhangjikai.guava;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import org.junit.Test;

import javax.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author Jikai Zhang
 * @date 2018-09-02
 */
public class BasicTools {
    @Test
    public void testPreconditions() {
        int i = 1;
        checkArgument(i > 1, "i less 1");
    }
    
    @Test
    public void testToString() {
        Person p = new Person("aa", 2);
        System.out.println(p);
    }
    
    @Test
    public void testCompareChain() {
       
       int i =  ComparisonChain.start().compare(1, 2).result();
       System.out.println(i);
    }
    
    @Test
    public void testOrdering() {
        // 按照字符串长度排序
        Ordering<String> byLengthOrdering = new Ordering<String>() {
            @Override
            public int compare(String left, String right) {
                return Ints.compare(left.length(), right.length());
            }
        };
        List<String> texts = Arrays.asList("1", "123", "12");
        // 取出集合中前 k 排序的元素，实现方法是先使用 Arrays 进行排序
        List<String> kMax = byLengthOrdering.greatestOf(texts, 2);
        System.out.println(kMax);
        String minLength = byLengthOrdering.min(texts);
        System.out.println(minLength);
        
    }
    
    private static class Person {
        String name;
        int age;
        
        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
    
    
        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("name", name)
                    .add("age", age)
                    .toString();
        }
    }
    
}
