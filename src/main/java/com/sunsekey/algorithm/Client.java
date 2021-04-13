package com.sunsekey.algorithm;

import com.sunsekey.algorithm.demo.TwoStackQueue;

public class Client {

    public static void main(String[] args) {
        singletonTest();
        twoStackQueueTest();
    }

    private static void twoStackQueueTest(){
        System.out.println("---- twoStackQueueTest ----");
        TwoStackQueue queue = new TwoStackQueue();
//        queue.push(1).push(2).push(3).push(4);
//        queue.push(1);
//        queue.push(2);
//        System.out.println(queue.pop());
//        System.out.println(queue.pop());
        queue.push(1);
        System.out.println(queue.pop());
        queue.printQueue();
    }

    /**
     * 实现一个单例类
     */
    private static void singletonTest() {
        System.out.println("---- singletonTest ----");
        MySingleton mySingleton = MySingleton.getInstance();
        System.out.println(mySingleton.getDesc());
    }


}
