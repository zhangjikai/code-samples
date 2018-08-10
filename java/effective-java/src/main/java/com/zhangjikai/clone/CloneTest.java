package com.zhangjikai.clone;

import java.util.Arrays;

/**
 * @author zhangjikai
 * Created on 2018-07-29
 */
public class CloneTest {
    public static void main(String[] args) throws InterruptedException {
        Person p = new Person("111", 222, new String[]{"111", "222"});
        Person cloneP = p.clone();
        System.out.println(cloneP);
        System.out.println(cloneP.phoneNumbers == p.phoneNumbers);
    }
}


class Person implements Cloneable {
    String name;
    int age;
    String[] phoneNumbers;

    public Person(String name, int age, String[] phoneNumbers) {
        this.name = name;
        this.age = age;
        this.phoneNumbers = phoneNumbers;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", phoneNumbers=" + Arrays.toString(phoneNumbers) +
                '}';
    }

    public Person clone() {
        try {
            Person p =  (Person) super.clone();
            p.phoneNumbers = phoneNumbers.clone();
            return p;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

