package com.sunsekey.practise.concurrent.aqs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * CyclicBarrier-"可复用栅栏"
 * CyclicBarrier是由<ReentrantLock + Condition>实现的
 * CyclicBarrier内部持有一个Generation对象，源码注释为：Each use of the barrier is represented as a generation instance
 * <即栅栏的每一次复用都由一个新的Generation对象来代表>
 *
 * await()->doAwait()：
 * 1）先lock.lock()，然后对index进行减一操作，如果这次减到0了，则所有线程都"到达栅栏"，
 * 则执行trip.signal.all()（trip是一个Condition实例），然后new一个新的Generation，如果有barrierAction的话，则执行之
 * 1.1）如果还没到0，则调用trip.await()进行阻塞。如果超时（设置了超时时间的话）或者被中断的话，会调用breakBarrier()，将generation设为broken，并且调用trip.signal()，唤醒等待的线程
 *
 * CountDownLatch: A synchronization aid that allows one or more threads to wait until a set of operations being performed in other threads completes
 * .'CountDownLatch: 一个或者多个线程，等待其他多个线程完成某件事情之后才能执行；)
 * CyclicBarrier : A synchronization aid that allows a set of threads to all wait for each other to reach a common barrier point.
 * (CyclicBarrier : 多个线程互相等待，直到到达同一个同步点，再继续一起执行。)
 *
 *
 */
public class CyclicBarrierDemo {

    // 请求的数量
    private static final int threadCount = 550;
    // 需要同步的线程数量
    private static final CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> {
        System.out.println("------当线程数达到之后，优先执行------");
    });

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        cyclicBarrierTest();
    }

    public static void cyclicBarrierTest() throws InterruptedException, BrokenBarrierException {
        int num = 4;
        CyclicBarrier cb = new CyclicBarrier(num, ()->{
            System.out.println("done");
        });
        ExecutorService es = Executors.newFixedThreadPool(num);
        for(int i = 0; i < num; i++){
            int finalI = i;
            es.submit(()->{
                try {
                    System.out.println(finalI + "running");
                    cb.await();
                    System.out.println(finalI + " done");
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        System.out.println("sub tasks are running...");
        es.shutdown();
    }

//    public static void main(String[] args) throws InterruptedException {
//        CyclicBarrierDemo cyclicBarrierDemo = new CyclicBarrierDemo();
//        cyclicBarrierDemo.cyclicBarrierTest();
//    }

    /**
     * 演示cyclicBarrier用法
     */
//    public void cyclicBarrierTest() throws InterruptedException {
//        int parties = 3;
//        CyclicBarrier cyclicBarrier = new CyclicBarrier(parties, () -> {
//            System.out.println("Thread " + Thread.currentThread().getName() + " do the clean up!");
//        });
//        startTasks(parties, cyclicBarrier);
////        startTasks(parties, cyclicBarrier); // 继续复用
//    }

//    private void startTasks(int parties, CyclicBarrier cyclicBarrier) throws InterruptedException {
//        List<Thread> threadList = new ArrayList<>();
//        for (int i = 0; i < parties; i++) {
//            Thread thread = new Thread(()->{
//                Random random = new Random();
//                int randomSleep = random.nextInt(3);
//                try {
//                    TimeUnit.SECONDS.sleep(randomSleep);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("Thread " + Thread.currentThread().getName() + " wait");
//                try {
//                    cyclicBarrier.await();
//                } catch (InterruptedException | BrokenBarrierException e) {
//                    e.printStackTrace();
//                }
////                    cyclicBarrier.await(2,TimeUnit.SECONDS);
//                System.out.println("Thread " + Thread.currentThread().getName() + " finish");
//            });
//            thread.setName("thread " + i);
//            threadList.add(thread);
//        }
//        for (int i = 0; i < threadList.size(); i++) {
//            if (i == (threadList.size() - 1)) {
//                TimeUnit.SECONDS.sleep(3);
//                threadList.get(i).start();
//                continue;
//            }
//            threadList.get(i).start();
//        }
//    }
}
