package com.zhangjikai.controller;

import com.zhangjikai.pojo.Message;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2016/1/15.
 */
@RestController
public class MainController {

    @RequestMapping("/hello/{player}")
    public Message message(@PathVariable String player) {
        Message msg = new Message(player, "hello " + player);
        return msg;
    }

    // 防止乱码，只有在requestMapping中设置produces才管用
    @RequestMapping(value = "/string", produces = {"application/json;charset=UTF-8"})
    public String hello() {
        return "你好啊";
    }
}
