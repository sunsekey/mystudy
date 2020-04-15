package com.sunsekey.practise.concurrent.aqs;

import lombok.SneakyThrows;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatch，也是基于AQS
 *
 *
 * CountDownLatch构造函数，为state设值
 * 阻塞/尝试获取锁过程：
 * 1）cd.await()->aqs.acquireSharedInterruptibly()->cd.sync.tryAcquireShared()<看state值是否为0了>
 *     ->为0则返回，不会阻塞；不为0，则doAcquireSharedInterruptibly()
 * 2）doAcquireSharedInterruptibly()，创建Node(addWaiter)，进入死循环，判断是不是跟在头节点后面，
 * 2.1）是的话尝试去"获得锁"（即看state是不是为0了），获得的话调用setHeadAndPropagate(不只设置自己为head，还需要通知其他等待的线程(doReleaseShared()))
 * 2.1.1) doReleaseShared主要是调用unparkSuccessor()去通知其他同样在等待着的节点线程
 * 2.2) 不是跟在头节点后或者"获取锁"失败，则阻塞线程（shouldParkAfterFailedAcquire(p, node) && parkAndCheckInterrupt()）
 *
 * 释放操作：
 * 1）tryReleaseShared()，如果state减到0了，那么返回true，还没到0就返回false。
 * 1.1）返回true的话，则调用通知后面的节点
 *
 * 总结：逻辑不是特别清晰，简单来说，假设ta,tb,tc三个线程在await着，队列譬如是head<-na<-nb<-nc。
 * 某个时候执行countDown，state值到0了，随即就会调用doReleaseShared()->unparkSuccessor()来通知后面一个节点(从head开始算)即na(//todo 可能不是na，貌似是从队尾开始，不过这个不是特别重要，后续再看)
 * 因为na本身在阻塞着，被唤醒后，在doAcquireSharedInterruptibly()的循环中，调用setHeadAndPropagate()，将自己设为head后，
 * 也是会调用doReleaseShared()->unparkSuccessor()，继续通知下一个节点nb，然后重复以上操作（因为还在死循环里）..直到所有在等待的线程都被唤醒了（即共享锁的核心思想）
 * （//todo 后续可继续研究）
 *
 * ps:要注意，await操作看着好像是独占操作，但它可以在多个线程中调用。当计数值等于0的时候，调用await的线程都需要知道，所以使用共享锁。
 *
 * await方法还有个限定阻塞时间的版本，具体调用aqs.doAcquireSharedNanos()(涉及了自旋操作，spinForTimeoutThreshold写死了1000ns。
 * 当超时在1000ns内，让线程在循环中自旋，否则阻塞线程。)
 *
 * 使用场景：
 * 1）可实现一个简单的外部服务健康检测工具。它开始时启动了n个线程类，这些线程将检查外部系统并通知闭锁(countDown)，并且启动类一直在闭锁上等待着(await())。
 * 一旦验证和检查了所有外部服务，那么启动类恢复执行，继续完成启动工作。
 * 2）//todo
 *
 * 参考：https://www.jianshu.com/p/7c7a5df5bda6?ref=myread
 */
public class CountDownLatchDemo {

    public static void main(String[] args) {
        countDownLatchDemo();
    }

    /**
     * 演示使用countDownLatch
     */
    private static void countDownLatchDemo() {
        int threadNum = 10;
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);

        try {
            int waitNum = 50;
            for (int i = 0; i < waitNum; i++) {
                new Thread(new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        System.out.println("thread " + Thread.currentThread().getName() + " await");
                        countDownLatch.await();
                        System.out.println("thread " + Thread.currentThread().getName() + " done");
                    }
                }).start();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < threadNum; i++) {
            new Thread(()->{
                System.out.println("Thread " + Thread.currentThread().getName() + " starts");
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
                System.out.println("Thread " + Thread.currentThread().getName() + " finished");
            }).start();
        }
    }
}
