package com.zhangjikai.java8.streams;

import java.util.function.Function;


class Animal {
    public void run() {
        System.out.println("animal run");
    }
}

class Cat extends Animal {
    public void jump() {
        System.out.println("cat jump");
    }
}

class WhiteCat extends Animal {
    public void printColor() {
        System.out.println("white");
    }
}


