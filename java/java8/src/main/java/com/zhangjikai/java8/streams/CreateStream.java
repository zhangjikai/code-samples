package com.zhangjikai.java8.streams;

import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 创建流
 * @author Jikai Zhang
 * @date 2017/12/12.
 */
public class CreateStream {

    @Test
    public void fromValue() {
        Stream<String> stream = Stream.of("aaa", "bbb", "ccc", "ddd");
        stream.map(String::toUpperCase).forEach(System.out::println);
    }

    @Test
    public void fromCollection() {
        List<String> list = new ArrayList<>();
        list.add("111");
        list.add("222");
        list.add("333");
        Integer sum = list.stream()
            .map(Integer::parseInt)
            .reduce(Integer::sum).get();
        System.out.println(sum);
    }

    @Test
    public void fromArray() {
        int[] arr = new int[]{1, 3, 5, 7};
        IntStream stream = Arrays.stream(arr);
        int sum = stream.sum();
        System.out.println(sum);

        String[] strArr = new String[]{"aaa", "bbb", "ccc", "ddd"};
        Stream<String> stringStream = Arrays.stream(strArr);
        stringStream.map(String::toUpperCase).forEach(System.out::println);
    }

    @Test
    public void fromFile() {
        long uniqueWords = 0;
        try (Stream<String> lines = Files.lines(Paths.get("data.txt"), Charset.defaultCharset())) {
            uniqueWords = lines.flatMap(line -> Arrays.stream(line.split(" "))).distinct().count();
            System.out.println(uniqueWords);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 由函数生成流
     */
    @Test
    public void fromIterate() {
        Stream.iterate(0, n -> n + 2)
            .limit(10)
            .forEach(System.out::println);
    }

    @Test
    public void fromGenerate() {
        Stream.generate(Math::random)
            .limit(5)
            .forEach(System.out::println);
    }


}
