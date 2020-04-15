package com.sunsekey.practise.concurrent.thread;

import lombok.SneakyThrows;

/**
 * 线程基础：wait、notify\notifyAll、sleep、join、yield
 */
public class ThreadBasicDemo {

    volatile static boolean shouldGo = false;

    public static void main(String[] args) throws InterruptedException {
//        threadJoinTest();
//        threadSleepTest();
        ThreadBasicDemo threadBasicDemo = new ThreadBasicDemo();
//        threadBasicDemo.threadSleepTest();
//        threadBasicDemo.threadNotifyTest();
        threadBasicDemo.threadYieldTest();
    }

    /**
     * wait + notify + notifyAll
     * wait使当前线程进入等待状态，会将锁释放。notify会唤醒等待线程中的其中一个
     * 而notifyAll会唤醒所有等待中的线程，但这些线程被唤醒后不是立刻都能得到执行,它们之间仍需通过争夺锁来获得执行权
     * 所以wait、notify、notifyAll一般都在synchronized下使用
     * @throws InterruptedException
     */
    public void threadNotifyTest() throws InterruptedException {
        Runnable aWaitTask = () -> {
            try {
                waitForSomething();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Runnable aNotifyTask = this::notifySomeone;
        new Thread(aWaitTask).start();
        new Thread(aWaitTask).start();
        new Thread(aWaitTask).start();
        Thread.sleep(200);

        new Thread(aNotifyTask).start();
    }
    public synchronized void notifySomeone() {
        while (!shouldGo) {
            shouldGo = true;
            System.out.println("you can go! --" + Thread.currentThread().getName());
//            notify();
            notifyAll();
        }
    }
    public synchronized void waitForSomething() throws InterruptedException {
        while (!shouldGo) {
            System.out.println("continues waiting-- " + Thread.currentThread().getName());
            wait();
            System.out.println("Go! -- " + Thread.currentThread().getName());
        }
        // notifyAll后先抢到锁的把竞争条件重置，使得后面拿到锁的人继续"困"在while循环中，要等下一次notify/notifyAll
        shouldGo = false;
    }

    /**
     * sleep
     * sleep使线程进入time_waiting状态，但不会释放锁。而且不能使用notifyAll/notify去唤醒
     */
    public void threadSleepTest() {
        Runnable sleepyTask = new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                sleepy();
            }
        };

        Thread sleepyThread1 = new Thread(sleepyTask);
        Thread sleepyThread2 = new Thread(sleepyTask);
        Thread wakeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                wake();
            }
        });
        sleepyThread1.start();
        sleepyThread2.start();
        wakeThread.start();
    }

    public synchronized void wake() {
        System.out.println("going to wake someone-- " + Thread.currentThread().getName());
        notify();
    }

    public synchronized void sleepy() throws InterruptedException {
        System.out.println("i am a sleepy thread-- " + Thread.currentThread().getName());
        Thread.sleep(2000);
        System.out.println("i am awoke-- " + Thread.currentThread().getName());
    }


    /**
     * join
     * join方法，让当前在running的线程暂停（wait）,直到调用join方法的线程执行完毕
     */
    public static void threadJoinTest() {
        Thread fatherThread = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Thread sonThread = new Thread(new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        System.out.println("son thread- " + Thread.currentThread().getName() + " running ");
                    }
                });
                sonThread.start();
                sonThread.join();
                System.out.println("father thread- " + Thread.currentThread().getName() + " running ");
            }
        });
        fatherThread.start();
    }

    /**
     * yield
     * yield是让步的意思，使当前正在执行的线程把运行机会交给线程池中拥有相同优先级的线程。
     * 它仅能使一个线程从运行状态转到可运行状态，而不是等待或阻塞状态，而且不会让出锁
     * 但不能保证使得当前正在运行的线程迅速转换到可运行的状态
     * 所以该demo很难看出效果..
     */
    private void threadYieldTest() throws InterruptedException {
        Runnable yieldTask = this::yieldThreadWorking;

        Thread yieldThread1 = new Thread(yieldTask);
        yieldThread1.setName("yield thread1");
        yieldThread1.start();
        Thread.sleep(10);
        Thread yieldThread2 = new Thread(yieldTask);
        yieldThread2.setName("yield thread2");
        yieldThread2.start();
    }

    public synchronized void yieldThreadWorking() {
        for (int i = 0; i < 10000; i++) {
            if (i % 10 == 0) {//
                Thread.yield();
            }
            System.out.println(i + "-- " + Thread.currentThread().getName());
        }
    }

}
