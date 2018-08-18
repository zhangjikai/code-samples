package com.zhangjikai.sample.generic;

/**
 * Created by Jikai Zhang on 2017/9/17.
 */
public class Fruit {
    protected String name;

    public Fruit() {
        this.name = "fruit";
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
