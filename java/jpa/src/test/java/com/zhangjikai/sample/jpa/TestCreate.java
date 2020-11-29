package com.zhangjikai.sample.jpa;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.util.concurrent.MoreExecutors;
import com.zhangjikai.sample.jpa.dto.UserRelation;
import com.zhangjikai.sample.jpa.dto.UserRelationDAO;
import com.zhangjikai.sample.jpa.dto.UserRelationRepository;

/**
 * @author Jikai Zhang
 * @date 2020-11-28
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestCreate {
    
    @Autowired
    private UserRelationRepository userRelationRepository;
    
    @Autowired
    private UserRelationDAO userRelationDAO;
    
    @Test
    public void testCreate() {
        int loop = 10000000;
        ExecutorService executorService = new ThreadPoolExecutor(100, 100,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1000),
                (r, executor) -> {
                    try {
                        executor.getQueue().put(r);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
    
        for(int i = 0; i < loop; i++) {
            executorService.execute(this::save);
        }
        MoreExecutors.shutdownAndAwaitTermination(executorService, 1, TimeUnit.DAYS);
        
    }
    
    private void save() {
        try {
            UserRelation userRelation = new UserRelation(ThreadLocalRandom.current().nextInt(0, 10),
                    ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE), System.currentTimeMillis());
            // userRelationRepository.save(userRelation);
            userRelationDAO.save(userRelation);
            // System.out.println(Thread.currentThread());
            
        } catch (Exception e) {
        }
    }
}
