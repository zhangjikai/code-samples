package com.zhangjikai.java8.streams;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Jikai Zhang
 * @date 2017/12/13.
 */
public class StreamOperation {

    @Test
    public void streamOnce() {
        Stream<Integer> stream = Stream.of(1, 3, 5);
        stream.filter(i -> i > 1).forEach(System.out::println);
        // 下面的代码会报错，因为流已经关闭了
        stream.filter(i -> i > 1).forEach(System.out::println);
    }

    @Test
    public void streamOperations() {
        Integer[] arr = new Integer[]{1, 2, 6, 1, 3, 8, 10};

        // filter: 根据条件过滤
        // forEach: 遍历流中的元素
        Arrays.stream(arr).filter(i -> i > 1).forEach(System.out::println);
        System.out.println();

        // distinct: 去重
        Arrays.stream(arr).distinct().forEach(System.out::println);
        System.out.println();

        // sorted: 排序
        Arrays.stream(arr).sorted().forEach(System.out::println);
        System.out.println();

        // skip: 跳过前面的元素
        Arrays.stream(arr).skip(2).forEach(System.out::println);
        System.out.println();

        // limit: 限制获取的元素数量
        Arrays.stream(arr).limit(2).forEach(System.out::println);
        System.out.println();

        // map: 元素映射， T -> R
        Arrays.stream(arr).map(String::valueOf).forEach(v -> System.out.println(v.length()));
        System.out.println();

        // anyMatch:任一元素匹配
        boolean result = Arrays.stream(arr).anyMatch(i -> i > 5);
        System.out.println(result);

        // noneMatch: 无元素匹配
        result = Arrays.stream(arr).noneMatch(i -> i > 5);
        System.out.println(result);

        // allMatch: 全部元素匹配
        result = Arrays.stream(arr).allMatch(i -> i > 5);
        System.out.println(result);

        // findAny: 返回流中任一元素
        Optional<Integer> optional = Arrays.stream(arr).findAny();
        System.out.println(optional.get());

        // findFirst: 返回流中的第一个元素
        optional = Arrays.stream(arr).findFirst();
        System.out.println(optional.get());

        // count: 统计流中元素个数
        long count = Arrays.stream(arr).count();
        System.out.println(count);
    }

    @Test
    public void useFlatMap() {
        Integer[][] array = new Integer[][]{
            new Integer[]{1, 3, 4},
            new Integer[]{1, 2, 3},
            new Integer[]{2, 2, 6}
        };

        List<Integer[]> results = Arrays.stream(array).distinct().collect(Collectors.toList());
        results.forEach(arr -> System.out.println(Arrays.toString(arr)));

        List<Integer> results2 = Arrays.stream(array).flatMap(Arrays::stream).distinct().collect(Collectors.toList());
        results2.forEach(System.out::println);
    }

    @Test
    public void useReduce() {
        Integer[] numbers = new Integer[] {1, 2, 4, 6, 10};

        // 求和
        int sum = Arrays.stream(numbers).reduce(0, Integer::sum);
        System.out.println(sum);

        // 求最大值
        Optional<Integer> o = Arrays.stream(numbers).reduce(Integer::max);
        System.out.println(o.get());

        // 求最小值
        o = Arrays.stream(numbers).reduce(Integer::min);
        System.out.println(o.get());
    }
}
