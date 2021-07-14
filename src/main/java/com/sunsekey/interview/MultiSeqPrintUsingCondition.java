package com.sunsekey.interview;

import lombok.SneakyThrows;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 写一个程序，创建3个线程，线程1负责打印A,
 * 线程2负责打印B， 线程3负责打印C，要求依次打印 ABC
 * ，共打印10轮（ABCABCABCABCABCABCABCABCABCABC）。
 * https://segmentfault.com/a/1190000021433079
 */
public class MultiSeqPrintUsingCondition {

    private static Lock lock = new ReentrantLock();
    private static Condition conditionA = lock.newCondition();
    private static Condition conditionB = lock.newCondition();
    private static Condition conditionC = lock.newCondition();

    private static int count = 0;
    private static int loop = 10;

    public static void main(String[] args) throws InterruptedException {

        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        TaskA taskA = new TaskA();
        TaskB taskB = new TaskB();
        TaskC taskC = new TaskC();

        threadPool.execute(taskA);
        threadPool.execute(taskB);
        threadPool.execute(taskC);

        threadPool.shutdown();
    }

    private static class TaskA implements Runnable {

        @SneakyThrows
        @Override
        public void run() {
            try {
                lock.lock();
                for (int i = 0; i < loop; i++) {
                    while ((count % 3) != 0) {
                        conditionA.await();
                    }
                    System.out.print("A");
                    count++;
                    conditionB.signal();
                }
            } finally {
                lock.unlock();
            }
        }
    }

    private static class TaskB implements Runnable {

        @SneakyThrows
        @Override
        public void run() {
            try {
                lock.lock();
                for (int i = 0; i < loop; i++) {
                    while ((count % 3) != 1) {
                        conditionB.await();
                    }
                    System.out.print("B");
                    count++;
                    conditionC.signal();
                }

            } finally {
                lock.unlock();
            }
        }
    }

    private static class TaskC implements Runnable {

        @SneakyThrows
        @Override
        public void run() {
            try {
                lock.lock();
                for (int i = 0; i < loop; i++) {
                    while ((count % 3) != 2) {
                        conditionC.await();
                    }
                    System.out.print("C");
                    count++;
                    conditionA.signal();
                }
            } finally {
                lock.unlock();
            }
        }
    }
}

