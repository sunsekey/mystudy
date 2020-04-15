package com.sunsekey.practise.concurrent.aqs;

/**
 * AQS:AbstractQueuedSynchronizer，一个用来构建锁和同步工具的"框架"（抽象类），包括常用的ReentrantLock、CountDownLatch、Semaphore等
 * AQS没有锁之类的概念，它有个<state>变量，这是一个由子类决定含义的“状态”。
 * 对于ReentrantLock来说，state是线程获取锁的次数；对于CountDownLatch来说，则表示计数值的大小。对于Semaphore来说，就是信号量值
 * state提供两种基本操作<获取>和<释放>
 *
 * 其他参考：https://www.cnblogs.com/dolphin0520/p/3920397.html
 */
public class AQSDemo {
}
