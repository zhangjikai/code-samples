package com.zhangjikai.controller;

import jdk.nashorn.internal.objects.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * @author Jikai Zhang
 * @date 2019-06-29
 */
@RestController
public class HelloController {
   
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);
    private static final List<Long> GLOBAL_SET = new ArrayList<>();
    
    @RequestMapping("/add")
    public Map<String, Object> addToMemory() {
        for (int i = 0; i < 1000; i++) {
            GLOBAL_SET.add(ThreadLocalRandom.current().nextLong());
        }
        logger.info("count {}", GLOBAL_SET.size());
        Map<String, Object> map = new HashMap<>();
        map.put("result", "success");
        return map;
    }
}
