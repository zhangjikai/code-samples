package com.zhangjikai.pojo;

/**
 * Created by Administrator on 2016/1/15.
 */
public class Message {
    String name;
    String text;

    public Message(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }
}
