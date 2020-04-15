package com.sunsekey.practise.javabasic._collection.concurrent;

import com.sun.org.apache.bcel.internal.generic.ACONST_NULL;
import lombok.SneakyThrows;

import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentLinkedQueueDemo {
    public static void main(String[] args) {
        ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
        Runnable consumer = new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                while (true) {
                    Integer item = queue.poll();
                    System.out.println(Thread.currentThread().getName() + " consume " + item);
                    TimeUnit.MILLISECONDS.sleep(200);
                }
            }
        };
        Runnable provider = new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                while (true) {
                    Random random = new Random();
                    Integer rInt = random.nextInt(100);
                    System.out.println(Thread.currentThread().getName() + " provide " + rInt);
                    queue.offer(rInt);
                    TimeUnit.MILLISECONDS.sleep(100);
                }
            }
        };
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(consumer);
        executorService.execute(provider);
        executorService.shutdown();
    }
}
