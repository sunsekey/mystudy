package com.sunsekey.practise.javabasic._collection.concurrent;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentHashMapDemo {

    public static void main(String[] args) throws InterruptedException {
        ConcurrentHashMap<Long, String> concurrentHashMap = new ConcurrentHashMap<>();
        Runnable task = () -> concurrentHashMap.put(Thread.currentThread().getId(), Thread.currentThread().getName());
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        int count = 10;
        while (count > 0) {
            executorService.execute(task);
            count--;
        }
        executorService.shutdown();
        Thread.sleep(100);
        for (Long threadId : concurrentHashMap.keySet()) {
            System.out.println("threadId: " + threadId + " threadName: " + concurrentHashMap.get(threadId));
        }
        concurrentHashMap.putIfAbsent(1L, "1");
        concurrentHashMap.replace(1L, "2");
    }
}
