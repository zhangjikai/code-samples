package com.zhangjikai.hash;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

import com.google.common.hash.Hashing;

/**
 * @author Jikai Zhang
 * @date 2019-10-19
 */
public class Main {
    
    private static final int MAX_COUNT = 10000;
    private static final int NODE_COUNT = 5;
    
    public static void main(String[] args) {
        check(Main::simpleHash);
        check(Main::consistencyHash);
    }
    
    public static void check(BiFunction<Integer, List<Integer>, List<Integer>> hashFunction) {
        List<Integer> originalList = IntStream.rangeClosed(1, MAX_COUNT).boxed().collect(toList());
        List<Integer> hashList = hashFunction.apply(NODE_COUNT, originalList);
        List<Integer> moreNodeHashList = hashFunction.apply(NODE_COUNT + 1, originalList);
        List<Integer> lessNodeHashList = hashFunction.apply(NODE_COUNT - 1, originalList);
        int moreNodeChange = MAX_COUNT;
        int lessNodeChange = MAX_COUNT;
        for (int i = 0; i < hashList.size(); i++) {
            if (Objects.equals(hashList.get(i), moreNodeHashList.get(i))) {
                moreNodeChange--;
            }
            if (Objects.equals(hashList.get(i), lessNodeHashList.get(i))) {
                lessNodeChange--;
            }
        }
        System.out.printf("add one node change: %.2f\n", moreNodeChange * 1.0 / MAX_COUNT);
        System.out.printf("delete one node change: %.2f\n", lessNodeChange * 1.0 / MAX_COUNT);
    }
    
    public static List<Integer> simpleHash(int nodeCount, List<Integer> nums) {
        return nums.stream()
                .map(n -> n % nodeCount)
                .collect(toList());
    }
    
    
    public static List<Integer> consistencyHash(int nodeCount, List<Integer> nums) {
        return nums.stream()
                .map(n -> Hashing.consistentHash(n, nodeCount))
                .collect(toList());
    }
}
