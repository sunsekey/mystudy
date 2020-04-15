package com.sunsekey.practise.javabasic._collection.concurrent;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * copy-on-write
 */
public class CopyOnWriteArrayListDemo {
    public static void main(String[] args) throws InterruptedException {

        CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        int limit = 10;
        for (int i = 0; i < limit; i++) {
            copyOnWriteArrayList.add(i);
        }
        Runnable readTask = () -> {
            // 使用copyOnWriteArrayList时，推荐使用迭代器的方式而不是普通for
            for (Integer item : copyOnWriteArrayList) {
                // 进入循环会创建一个迭代器，迭代器会持有某个时刻的array，遍历期间用的都是这个array，所以这期间遍历出来的数据，有可能是脏数据，因为只是个快照
                System.out.println("read task read " + item);
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Runnable modifyTask = () -> {
            try {
                //等上面的迭代器先创建了
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Random random = new Random();
            for (int i = 0; i < 10; i++) {
                int item = random.nextInt(copyOnWriteArrayList.size());
                System.out.println(Thread.currentThread().getName() + " add " + item);
                copyOnWriteArrayList.add(item);
            }
        };
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        executorService.execute(readTask);
        executorService.execute(modifyTask);
//        for (int i = 0; i < 10; i++) {
//            executorService.execute(modifyTask);
//        }
        executorService.shutdown();
    }


}
