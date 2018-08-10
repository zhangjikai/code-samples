package com.zhangjikao.sample.generic;

/**
 * Created by Jikai Zhang on 2017/9/17.
 * <p>
 * 在类声明时使用 extends 表示类型的上界，即 T 可以是 Fruit 或者 Fruit 的子类。
 */
public class Container<T extends Fruit> {

    public void add(T fruit) {
        System.out.println(fruit.getName());
    }
}
