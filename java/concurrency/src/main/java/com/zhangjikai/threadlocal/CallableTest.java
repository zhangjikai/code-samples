package com.zhangjikai.threadlocal;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Created by Jikai Zhang on 2017/5/7.
 */
public class CallableTest {

   public static class CustomCallable<T> implements Callable<T>{

       @Override
       public T call() throws Exception {
           System.out.println("call");
           return (T)(new Integer(10));
       }
   }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Callable<Integer> callable = new CustomCallable();
        FutureTask<Integer> future = new FutureTask(callable);
        Thread thread = new Thread(future);
        thread.start();
        thread.join();
        System.out.println(future.get());

    }
}


