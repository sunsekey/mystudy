package com.sunsekey.interview;

import java.util.concurrent.locks.LockSupport;

/**
 * 写一个程序，创建3个线程，线程1负责打印A,
 * 线程2负责打印B， 线程3负责打印C，要求依次打印 ABC
 * ，共打印10轮（ABCABCABCABCABCABCABCABCABCABC）。
 * https://segmentfault.com/a/1190000021433079
 */
public class MultiSeqPrintUsingLockSupport {

    private static int count = 10;
    static Thread threadA, threadB, threadC;

    public static void main(String[] args) throws InterruptedException {

        threadA = new Thread(()->{
           for(int i = 0; i < count; i ++){
               System.out.print("A");
               LockSupport.unpark(threadB);
               LockSupport.park();
           }
        });
        threadB = new Thread(()->{
            for(int i = 0; i < count; i ++){
                LockSupport.park();
                System.out.print("B");
                LockSupport.unpark(threadC);
            }
        });
        threadC = new Thread(()->{
            for(int i = 0; i < count; i ++){
                LockSupport.park();
                System.out.print("C");
                LockSupport.unpark(threadA);
            }
        });
        threadA.start();
        threadB.start();
        threadC.start();
        System.out.println();
    }

}

