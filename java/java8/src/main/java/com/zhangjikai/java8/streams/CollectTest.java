package com.zhangjikai.java8.streams;

import com.zhangjikai.java8.common.Person;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author Jikai Zhang
 * @date 2017/12/17.
 */
public class CollectTest {
    List<Person> persons;

    @Before
    public void setUp() {
        persons = new ArrayList<>();
        persons.add(new Person("1", "aaa", "male"));
        persons.add(new Person("2", "bbb", "female"));
        persons.add(new Person("3", "ccc", "female"));
        persons.add(new Person("4", "aaa", "female"));
        persons.add(new Person("5", "bbb", "male"));
        persons.add(new Person("6", "bbb", "male"));
        persons.add(new Person("7", "aaa", "male"));
        persons.add(new Person("8", "ccc", "male"));
    }

    @Test
    public void testCollection() {

        Integer[] array = new Integer[]{1, 3, 1, 4, 6, 4, 7};

        // 收集为 List（ArrayList），输出结果为：[1, 3, 1, 4, 6, 4, 7]
        List<Integer> list = Arrays.stream(array).collect(Collectors.toList());
        System.out.println(list);

        // 收集为 Set（HashSet），输出结果为 [1, 3, 4, 6, 7]
        Set<Integer> set = Arrays.stream(array).collect(Collectors.toSet());
        System.out.println(set);

        /*
         * 在前面的示例中，toList 只能生成 ArrayList, toSet 只能生成 HashSet
         * 所以又额外提供了一个 toCollection 允许用户传入一个 Supplier 对象，用于自定义集合类的类型
         */
        Collection<Integer> c = Arrays.stream(array).collect(Collectors.toCollection(LinkedList::new));
        System.out.println(c);

        /*
         * 收集为 map（HashMap），第一个参数为 key 的映射，第二个参数为 value 的映射。
         * 这里需要注意的是 key 值不能有重复。输出结果为：{1=1, 3=1, 4=1, 6=1, 7=1}
         */
        array = new Integer[]{1, 3, 6, 4, 7};
        Map<Integer, Integer> map = Arrays.stream(array).collect(Collectors.toMap(i -> i, i -> 1));
        System.out.println(map);

        /*
         * 第三个参数为冲突合并函数，当 key 值冲突时，会调用该函数合并 value 值
         * 输出结果为：{1=2, 3=3, 4=8, 6=6, 7=7}
         */
        array = new Integer[]{1, 3, 1, 4, 6, 4, 7};
        map = Arrays.stream(array).collect(Collectors.toMap(i -> i, i -> i, (a, b) -> a + b));
        System.out.println(map);

        /*
         * 第四个参数用来自定义 Map 的类型
         */
        map = Arrays.stream(array).collect(Collectors.toMap(i -> i, i -> i, (a, b) -> a + b, TreeMap::new));
        System.out.println(map);
    }

    @Test
    public void testSummarize() {
        Integer[] array = new Integer[]{1, 3, 6, 8, 3, 2};

        // 求和
        long count = Arrays.stream(array).collect(Collectors.counting());
        System.out.println(count);

        // 求最大值
        Optional<Integer> max = Arrays.stream(array).collect(Collectors.maxBy(Comparator.comparing(i -> i)));
        System.out.println(max.get());

        // 求最小值
        Optional<Integer> min = Arrays.stream(array).collect(Collectors.minBy(Comparator.comparing(i -> i)));
        System.out.println(min.get());

        // 求和， Collectors.summingLong 和 Collectors.summingDouble 用于 long 和 double 类型的求和
        int total = Arrays.stream(array).collect(Collectors.summingInt(i -> i));
        System.out.println(total);

        // 求平均数， Collectors.averagingLong 和 Collectors.averagingDouble 用于求 long 和 double 类型的平均值
        // 需要注意的是返回值均为 double
        double average = Arrays.stream(array).collect(Collectors.averagingInt(i -> i));
        System.out.println(average);

        // 字符串连接
        String text = Arrays.stream(array).map(String::valueOf).collect(Collectors.joining(", "));
        System.out.println(text);

        /**
         * 获得多个统计信息，包括 sum, average, min, max 和 count
         *
         * Collectors.summarizingLong 和 Collectors.summarizingDouble 用于统计 long 和 double 类型
         */
        IntSummaryStatistics statistics = Arrays.stream(array).collect(Collectors.summarizingInt(i -> i));
        System.out.println(statistics);
    }

    @Test
    public void testReducing() {
        Integer[] array = new Integer[]{1, 3, 6, 8, 3, 2};

        int total = Arrays.stream(array).collect(Collectors.reducing(0, Integer::sum));
        System.out.println(total);

        Optional<Integer> optional = Arrays.stream(array).collect(Collectors.reducing(Integer::sum));
        System.out.println(optional.get());
    }

    @Test
    public void testGroup() {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("1", "aaa", "male"));
        persons.add(new Person("2", "bbb", "female"));
        persons.add(new Person("3", "ccc", "female"));
        persons.add(new Person("4", "aaa", "female"));
        persons.add(new Person("5", "bbb", "male"));
        persons.add(new Person("6", "bbb", "male"));
        persons.add(new Person("7", "aaa", "male"));
        persons.add(new Person("8", "ccc", "male"));

        // 按 country 分组
        Map<String, List<Person>> personsByCity = persons.stream().collect(Collectors.groupingBy(Person::getCountry));
        System.out.println(personsByCity);

        // 多级分组，先按 country，再按 gender 分组
        Map<String, Map<String, List<Person>>> personsByCityGender = persons.stream().collect(
            Collectors.groupingBy(Person::getCountry, Collectors.groupingBy(Person::getGender)));
        System.out.println(personsByCityGender);

        // 按照 subgroup 分组收集
        Map<String, Long> countriesCount = persons.stream().collect(Collectors.groupingBy(
            Person::getCountry, Collectors.counting()));
        System.out.println(countriesCount);


    }

    @Test
    public void testOthers() {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("1", "aaa", "male"));
        persons.add(new Person("2", "bbb", "female"));
        persons.add(new Person("3", "ccc", "female"));
        persons.add(new Person("4", "aaa", "female"));
        persons.add(new Person("5", "bbb", "male"));
        persons.add(new Person("6", "bbb", "male"));
        persons.add(new Person("7", "aaa", "male"));
        persons.add(new Person("8", "ccc", "male"));

        Set<String> countrySet = persons.stream().collect(
            Collectors.collectingAndThen(Collectors.groupingBy(Person::getCountry), Map::keySet)
        );
        System.out.println(countrySet);

        countrySet = persons.stream().collect(
            Collectors.mapping(Person::getCountry, Collectors.toSet())
        );
        System.out.println(countrySet);


    }


}
