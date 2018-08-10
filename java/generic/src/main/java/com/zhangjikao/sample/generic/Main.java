package com.zhangjikao.sample.generic;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jikai Zhang on 2017/9/17.
 */
public class Main {

    public static void main(String[] args) {
        List<Fruit> fruitList = new ArrayList<>();
        List<Apple> appleList = new ArrayList<>();
        List<RedApple> redAppleList = new ArrayList<>();

        // extends 接受  <= T 的类型
        List<? extends Object> extendList = new ArrayList<String>();
        extendList = new ArrayList<Integer>();

        // super 接受 >= T 的类型
        List<? super Integer> superList = new ArrayList<Number>();
        superList = new ArrayList<Object>();


        // 使用 extends 无法往 list 添加数据，因为编译器无法判断添加的数据是 fruits
        // 指向的是包含哪个子类型的 ArrayList，例如如果 fruits = new ArrayList<Apple>(),
        // 那么就不能添加 Fruit类型，如果 fruits = new ArrayList<RedApple>(),
        // 那么就不能添加 Fruit 和 Apple 类型。所以编译器无法判断 fruits 能装入和不能装入的类型，,
        // 所以对于使用 extends 的泛型类，索性就不允许其添加数据。
        //
        // 但是不论是 new ArrayList<Apple> 还是 new ArrayList<RedApple>，里面装的肯定是一个
        // Fruit 对象，所以在获取时可以直接使用 Fruit fruit = fruits.get(0)
        List<? extends Fruit> fruits = new ArrayList<>();
        // 下面的代码会报错
        // fruits.add(new Fruit());
        // fruits.add(new Apple());
        Fruit fruit = fruits.get(0);


        // 使用 super 可以往 list 里面添加 Fruit 以及 Fruit 的子类,因为不论
        // fruits2 = new ArrayList<Fruit>() 还是
        // fruit = new ArrayList<Object>()，肯定都可以接受 Fruit 及其子类的数据
        //
        // 但是在获取数据时，不知道 ArrayList 到底用的是 Fruit 的那个超类，所以
        // 只能返回 Object 类型。
        List<? super Fruit> fruits2 = new ArrayList<>();
        fruits2.add(new Fruit());
        fruits2.add(new Apple());
        fruits2.add(new RedApple());
        Object object = fruits2.get(0);
        // 下面的代码会报错
        // fruit = fruits2.get(0);

        generic(fruitList, redAppleList);
    }

    public static void generic(List<? super Apple> appleList, List<? extends Apple> appleList2) {
        for (Apple apple : appleList2) {
            // do something
            appleList.add(apple);
        }
    }

    public static void classDeclareGeneric() {
        Fruit fruit = new Fruit();
        Apple apple = new Apple();
        RedApple redApple = new RedApple();


        Container<Fruit> fruitContainer = new Container<>();
        fruitContainer.add(fruit);
        fruitContainer.add(apple);

        Container<Apple> appleContainer = new Container<>();
        appleContainer.add(apple);
        // 下面的代码是错误的，不能添加 Apple 的超类
        // appleExtendContainer.add(fruit);

        // 下面的代码是错误的，因为ExtendContainer 限制类型只能是 Fruit 或者 Fruit 的子类
        // ExtendContainer<Object> objectExtendContainer = new ExtendContainer<>();
    }
}
