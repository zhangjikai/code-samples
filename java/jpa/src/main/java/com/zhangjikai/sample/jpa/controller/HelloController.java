package com.zhangjikai.sample.jpa.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.util.concurrent.MoreExecutors;
import com.zhangjikai.sample.jpa.dto.UserRelation;
import com.zhangjikai.sample.jpa.dto.UserRelationDAO;
import com.zhangjikai.sample.jpa.dto.UserRelationRepository;

/**
 * @author Jikai Zhang
 * @date 2020-11-28
 */
@RestController
public class HelloController {
    
    @Autowired
    private UserRelationRepository userRelationRepository;
    
    @Autowired
    private UserRelationDAO userRelationDAO;
    
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }
    
    @RequestMapping("/create")
    public String create() {
        UserRelation userRelation = new UserRelation(23333333, 1, System.currentTimeMillis());
        // userRelationRepository.save(userRelation);
        userRelationDAO.save(userRelation);
        return "success";
    }
}
