package com.sunsekey.practise.concurrent.thread;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 创建线程的三种方式：1）继承Thread 2）实现Runnable接口 3）Callable和Future、FutureTask
 *
 * ps:
 * 1）start和run的区别：start方法是使线程从NEW进入"READY"状态（实际Java中只有Runnable,其包含了Ready和Running），并不一定马上执行线程；run方法是线程得到执行时去调用的方法，不应该由客户端直接调用
 * 2）三种方式的区别：
 * 实现Runnable、Callable优势是：线程类只是实现了Runnable接口或Callable接口，还可以继承其他类。
 * 在这种方式下，多个线程可以共享同一个target对象，所以非常适合多个相同线程来处理同一份资源的情况，从而可以将CPU、代码和数据分开，形成清晰的模型，较好地体现了面向对象的思想。
 * Callable接口针对可返回结果的调用。
 * 而继承方式相对简单，如果需要访问当前线程，则无需使用Thread.currentThread()方法，直接使用this即可获得当前线程。
 *
 */
public class ThreadCreationDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("--------main thread " + Thread.currentThread() + "--------");
        runThreadByExtend();
        runThreadByImplRunnable();
//        runThreadByCallable();
    }

    public static void runThreadByExtend() {
        Thread myThread  = new MyThread();
        myThread.start();
    }

    public static void runThreadByImplRunnable() {
        Runnable runnable = () -> {
            int i = 10;
            while (i > 0) {
                System.out.println("runThreadByImplRunnable -- " + Thread.currentThread().getName() + " i" + i);
                i--;
            }
        };
        new Thread(runnable).start();
    }

    public static void runThreadByCallable() throws ExecutionException, InterruptedException {
        /**
         * <Callable>
         * Callable用于定义一个可返回结果或者抛出异常的任务
         */
        Callable<Integer> task = () -> {
            Random random = new Random();
            System.out.println("call -- " + Thread.currentThread().getName());
            return random.nextInt(100);
        };

        ExecutorService executor = Executors.newCachedThreadPool();
        // submit实际返回也是一个FutureTask
        Future<Integer> future = executor.submit(task);
        System.out.println(future.get());
        executor.shutdown();
    }


    public static class MyThread extends Thread{
        @Override
        public void run() {
            int i = 100;
            while (i > 0) {
                System.out.println("runThreadByExtend -- " + Thread.currentThread().getName() + " i" + i);
                i--;
            }
        }
    }
}
