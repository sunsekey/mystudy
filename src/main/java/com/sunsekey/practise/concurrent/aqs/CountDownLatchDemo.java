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
 * 1）某一线程在开始运行前等待 n 个线程执行完毕。将 CountDownLatch 的计数器初始化为 n ：new CountDownLatch(n)，每当一个任务线程执行完毕，
 *    就将计数器减 1 countdownlatch.countDown()，当计数器的值变为 0 时，在CountDownLatch上 await() 的线程就会被唤醒。一个典型应用场景就是启动一个服务时，主线程需要等待多个组件加载完毕，之后再继续执行。
 * 2）实现多个线程开始执行任务的最大并行性。注意是并行性，不是并发，强调的是多个线程在某一时刻同时开始执行。
 *    类似于赛跑，将多个线程放到起点，等待发令枪响，然后同时开跑。做法是初始化一个共享的 CountDownLatch 对象，将其计数器初始化为 1 ：new CountDownLatch(1)，多个线程在开始执行任务前首先 coundownlatch.await()，当主线程调用 countDown() 时，计数器变为 0，多个线程同时被唤醒。
 *
 * CountDownLatch 的不足：
 * CountDownLatch 是一次性的，计数器的值只能在构造方法中初始化一次，之后没有任何机制再次对其设置值，
 * 当 CountDownLatch 使用完毕后，它不能再次被使用。
 *
 * 参考：https://www.jianshu.com/p/7c7a5df5bda6?ref=myread
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        countDownLatchDemo();
    }

    /**
     * 演示使用countDownLatch
     */
    private static void countDownLatchDemo() throws InterruptedException {
        CountDownLatch cdl = new CountDownLatch(4);
        System.out.println("start..");
        cdl.countDown();
        cdl.countDown();
        cdl.await();
        System.out.println("all done..");


    }
}
