package com.sunsekey.practise.concurrent.aqs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Semaphore-信号<量>，是用来<控制同时访问特定资源的线程数量>，它通过协调各个线程，保证合理的使用公共资源。
 *
 * 获取-acquire()
 * 核心都是调用aqs的acquireSharedInterruptibly()->tryAcquireShared()->doAcquireSharedInterruptibly()方法，
 * 大致原理和CountDownLatch差不多，也是"共享锁"。doAcquireSharedInterruptibly是如果是头节点后面一个节点，即尝试获取"锁"，然后设置自己为头节点，然后唤醒下一个节点
 * 有点不同的是，获取操作时，会使state值减n(n为acquire所传permits值)，state可以理解为许可证的数量。
 *
 * 释放-release()
 * releaseShared()->tryReleaseShared()->doReleaseShared() 唤醒头节点后的一个节点去尝试"获得锁"
 * 和获取相反，释放操作时，会使state值加n(n为acquire所传permits值)
 *
 * 公平信号量/非公平信号量（和ReentrantLock的实现机制差不多，即非公平在不会判断自己前面是否有在等待的节点，直接去"抢"锁）
 *
 * 使用场景：
 * 1）流量控制，几十个线程处理数据并写入数据库，但数据库连接数只有10个，则可以用信号里控制访问资源的线程数
 * 2）
 * todo 使用场景
 *
 *
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        SemaphoreDemo semaphoreDemo = new SemaphoreDemo();
        semaphoreDemo.semaphoreTest();
    }

    private void semaphoreTest() {
        Semaphore semaphore = new Semaphore(3);
        ExecutorService executor = Executors.newCachedThreadPool();
        Runnable task = ()->{
            try {
//                semaphore.acquire(3);
                semaphore.acquire();
                System.out.println("Thread " + Thread.currentThread().getName() + " acquired the resource and start working..");
                TimeUnit.MILLISECONDS.sleep(1000);
                semaphore.release();
//                semaphore.release(3);
                System.out.println("Thread " + Thread.currentThread().getName() + " finishing working and released the resource..");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        for (int i = 0; i < 8; i++) {
            executor.execute(task);
        }
    }

}
