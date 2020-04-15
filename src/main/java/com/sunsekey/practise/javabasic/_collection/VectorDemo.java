package com.sunsekey.practise.javabasic._collection;

import lombok.SneakyThrows;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VectorDemo {
    public static void main(String[] args) {
        Vector<Integer> v = new Vector<>();
        for (int i = 0; i < 100; i++) {
            v.add(i);
        }
        int c = v.size();
        Runnable task = new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                for (int i = 0; i < c ; i++) {
                    System.out.println(Thread.currentThread() + " delete " + i);
                    v.remove(i); // may cause ArrayIndexOutOfBoundsException
                }
            }
        };
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.execute(task);
        }
        executorService.shutdown();
    }
}
