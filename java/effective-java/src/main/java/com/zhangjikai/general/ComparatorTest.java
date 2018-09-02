package com.zhangjikai.general;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;

/**
 * @author Jikai Zhang
 * @date 2018-08-26
 */
public class ComparatorTest {
    public static void main(String[] args) {
        Comparator<Integer> comparator = (i, j) -> (i < j) ? -1 : (i == j ? 0 : 1);
        System.out.println(comparator.compare(100000, 100000));
    
        HashSet<Node> hashSet = new HashSet<>();
        hashSet.add(new Node(1));
        hashSet.add(new Node(1));
        System.out.println(hashSet.size());
    }
    
    private static class Node{
        private int value;
    
        public Node(int value) {
            this.value = value;
        }
    
        @Override
        public boolean equals(Object obj) {
            return value == ((Node)obj).value;
        }
    
        @Override
        public int hashCode() {
            return Objects.hashCode(value);
        }
    }
}
