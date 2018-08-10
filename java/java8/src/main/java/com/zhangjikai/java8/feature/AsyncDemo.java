package com.zhangjikai.java8.feature;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author Jikai Zhang
 * @date 2018-05-04
 */
public class AsyncDemo {
    
    public static double calPrice() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Random().nextDouble();
    }
    
    public static int calIntPrice() {
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Random().nextInt();
    }
    
    public static int convertPrice(double d) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return (int) (100 * d);
    }
    
    
    public static void work() throws ExecutionException, InterruptedException {
        // 异步执行
        Future<Double> future = CompletableFuture.supplyAsync(AsyncDemo::calPrice);
        System.out.println(future.get());
        
        // 执行完异步之后，再执行 thenApply 里的逻辑
        Future<Integer> intFuture = CompletableFuture.supplyAsync(AsyncDemo::calPrice).thenApply(AsyncDemo::convertPrice);
        System.out.println(intFuture.get());
        
        // thenAccept, 纯消费，无返回值
        CompletableFuture.supplyAsync(AsyncDemo::calPrice).thenAccept(System.out::println);
        
        // thenCompose 合并两个有先后次序的异步任务
        intFuture = CompletableFuture.supplyAsync(AsyncDemo::calPrice)
                .thenCompose(d -> CompletableFuture.supplyAsync(() -> convertPrice(d)));
        System.out.println(intFuture.get());
        
        // thenCombine 合并两个五依赖的异步任务
        Future<String> strFuture = CompletableFuture.supplyAsync(AsyncDemo::calPrice)
                .thenCombine(CompletableFuture.supplyAsync(AsyncDemo::calIntPrice), (x, y) -> x + "-" + y);
        System.out.println(strFuture.get());
        
        // 使用自定义线程池
        ExecutorService service = Executors.newSingleThreadExecutor();
        future = CompletableFuture.supplyAsync(AsyncDemo::calPrice, service);
        System.out.println(future.get());
        service.shutdown();
        
        // allOf 等待所有的 future 完成, anyOf 等待任何一个完成
        // CompletableFuture[] futures = findPricesStream("myPhone").
        //         map(f -> f.thenAccept(System.out::println))
        //         .toArray(size -> newCompletableFuture[size]);
        // CompletableFuture.allOf(futures).join();
        
       
    }
    
    public static Future<Integer> getAndConvertPrice() {
        
        // CompletableFuture<Double> future = CompletableFuture.supplyAsync(AsyncDemo::calPrice);
        // return future.thenCompose(d -> CompletableFuture.supplyAsync(() -> convertPrice(d)));
        // thenCompose 组合两个completeFuture
        return CompletableFuture.supplyAsync(AsyncDemo::calPrice)
                .thenCompose(d -> CompletableFuture.supplyAsync(() -> convertPrice(d)));
        
    }
    
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Timer.start();
        // Future<Double> futurePrice = getPriceAsync();
        // Timer.end(false);
        //
        // double price = futurePrice.get();
        // Timer.end();
        // System.out.println(price);
        
        // Timer.start();
        // Future<Integer> future = getAndConvertPrice();
        // int p = future.get();
        // Timer.end();
        // System.out.println(p);
        work();
    }
    
}
