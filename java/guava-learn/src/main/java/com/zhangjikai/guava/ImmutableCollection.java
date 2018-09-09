package com.zhangjikai.guava;

import com.google.common.collect.*;
import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Jikai Zhang
 * @date 2018-09-02
 */
public class ImmutableCollection {
    
    @Test
    public void testImmutableBuilder() {
        Map<String, String> map = ImmutableMap.<String, String>builder()
                .put("1", "s1")
                .put("2", "s2")
                .build();
        System.out.println(map);
        ImmutableSet<Integer> set = ImmutableSet.of(3, 2, 1);
        List<Integer> list = set.asList();
        System.out.println(list);
    }
    
    @Test
    public void testMultiSet() {
        Multiset<String> s = HashMultiset.create();
        s.add("1");
        s.add("1");
        s.add("2");
        System.out.println(s.size());
        System.out.println(s.count("1"));
        System.out.println(s);
    }
    
    @Test
    public void testMultiMap() {
        // 通过builder 创建
        ListMultimap<Integer, Integer> listMultimap = MultimapBuilder.treeKeys().arrayListValues().build();
        SetMultimap<Integer, Integer> setMultimap = MultimapBuilder.treeKeys().hashSetValues().build();
        
        // 通过子类创建
        listMultimap = ArrayListMultimap.create();
        setMultimap = HashMultimap.create();
        
        listMultimap.put(1, 2);
        listMultimap.put(1, 3);
        System.out.println(listMultimap);
        System.out.println(listMultimap.get(1).size());
        
        Map<Integer, Collection<Integer>> map = listMultimap.asMap();
        System.out.println(map);
    }
    
    @Test
    public void testBiMap() {
        BiMap<Integer, String> biMap = HashBiMap.create();
        biMap.put(1, "111");
        System.out.println(biMap.get(1));
        System.out.println(biMap.inverse().get("111"));
        
        // 下面的代码会报错，需要保证 value 也是唯一的
        // biMap.put(2, "111");
        
        // forcePUt 会删除掉 key = 1 的记录
        biMap.forcePut(2, "111");
        System.out.println(biMap.get(1));
        System.out.println(biMap.inverse().get("111"));
    }
    
    @Test
    public void testTable() {
        Table<Integer, Integer, String> table = HashBasedTable.create();
        table.put(1, 1, "11");
        table.put(1, 2, "12");
        table.put(2, 1, "21");
        System.out.println(table.row(1));
        System.out.println(table.column(1));
        System.out.println(table.get(1, 2));
    }
    
    @Test
    public void testClassToInstanceMap() {
        ClassToInstanceMap<Number> numberDefaults = MutableClassToInstanceMap.create();
        numberDefaults.putInstance(Integer.class, 0);
        numberDefaults.putInstance(Long.class, 1L);
        System.out.println(numberDefaults.get(Integer.class));
        System.out.println(numberDefaults);
    }
    
    @Test
    public void testRangeSet() {
        RangeSet<Integer> range = TreeRangeSet.create();
        range.add(Range.openClosed(1, 3));
        range.add(Range.closedOpen(4, 5));
        System.out.println(range.contains(1));
        System.out.println(range.contains(3));
    }
    
    @Test
    public void testRangeMap() {
        RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
        rangeMap.put(Range.openClosed(1, 3), "1-3");
        System.out.println(rangeMap.get(1));
        System.out.println(rangeMap.get(2));
    }
    
    @Test
    public void testStaticConstructor() {
        List<Integer> list = Lists.newArrayList(1, 2, 3);
        System.out.println(list);
        Set<Integer> set = Sets.newHashSet(1, 2, 3);
        System.out.println(set);
    }
}
