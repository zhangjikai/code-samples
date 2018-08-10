package com.zhangjikai.java8.common;

/**
 *
 * @author Jikai Zhang
 * @date 2017/12/18.
 */
public class Person {
    private String name;
    private String country;
    private String gender;

    public Person() {
    }

    public Person(String name, String country, String gender) {
        this.name = name;
        this.country = country;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Person{" +
            "name='" + name + '\'' +
            ", country='" + country + '\'' +
            ", gender='" + gender + '\'' +
            '}';
    }
}
