package com.sunsekey.practise.concurrent.exchanger;

import lombok.SneakyThrows;

import java.util.*;
import java.util.concurrent.*;

/**
 * Exchanger 一般用于两个工作线程之间交换数据
 * -此类提供对外的操作是同步的
 * -用于<成对出现的线程>之间交换数据；
 * -可以视作<双向的同步队列>
 * -可应用于基因算法、流水线设计等场景
 * （摘自官方api文档）
 *
 * exchanger提供了一个在<阻塞点>用于线程成对交换数据的同步机制，在<发生交换前两者各自干自己的活>，互不干扰，只在交换点阻塞。
 * （一个小事例加深理解：A负责盛水，B负责浇水。A若盛满水了则等待B把水用光过来取水；B若用光水了则等待A把水盛满送过来）
 *
 * // todo demo有点小问题，后续修正
 */
public class ExchangerDemo {

    static LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

    static class ExchangerFiller implements Runnable {
        private Exchanger<LinkedBlockingQueue<Integer>> exchanger;

        ExchangerFiller(Exchanger<LinkedBlockingQueue<Integer>> exchanger) {
            this.exchanger = exchanger;
        }

        @SneakyThrows
        @Override
        public void run() {
            while (!Thread.interrupted()) {
                Random random = new Random();
                int bound = 100;
                // 模拟装水过程，假设装5个元素则装满水
                while (queue.size() != 5) {
                    queue.add(random.nextInt(bound));
                    System.out.println("装水者正在装水:" + queue);
                }

                queue = exchanger.exchange(queue);
                System.out.println("来自用水者拿回来的水盆: " + queue);
            }
        }
    }

    static class ExchangerUser implements Runnable {
        private Exchanger<LinkedBlockingQueue<Integer>> exchanger;

        ExchangerUser(Exchanger<LinkedBlockingQueue<Integer>> exchanger) {
            this.exchanger = exchanger;
        }

        @SneakyThrows
        @Override
        public void run() {
            while (!Thread.interrupted()) {
                //水没了，等待装水者把水装满
                if (queue.isEmpty()) {
                    queue = exchanger.exchange(queue);
                    System.out.println("来自装水者的水盆: " + queue);

                }
                queue.clear();
                System.out.println("用水者把水用完: " + queue);

            }
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        Exchanger<LinkedBlockingQueue<Integer>> exchanger = new Exchanger<>();
        ExchangerFiller filler = new ExchangerFiller(exchanger);
        ExchangerUser user = new ExchangerUser(exchanger);
        exec.execute(filler);
        exec.execute(user);
        exec.shutdown();
        try {
            exec.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
