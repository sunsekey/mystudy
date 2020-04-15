package com.sunsekey.practise.concurrent.threadpool;

import lombok.Data;
import lombok.SneakyThrows;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池
 *
 * ThreadPoolExecutor的重要参数（即线程池的核心重要参数）
 * 1）corePoolSize，<核心线程数>。当线程数小于核心线程数时（还未满，就会一直增），即使有线程空闲，线程池也会优先创建新线程处理。核心线程会一直存活，即使没有任务需要执行
 * 2）maximumPoolSize是线程池的最大上限，maximumPoolSize减去corePoolSize即是非核心线程数，或者叫空闲线程。
 * 3）keepAliveTime指明<空闲线程的存活时间>，超出存活时间的空闲线程就会被回收。
 * 4）unit，时间单位
 *
 * todo 如何合理配置线程池的大小
 *
 * 参考：https://www.cnblogs.com/dolphin0520/p/3932921.html
 *
 */
public class ThreadExecutorDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ThreadExecutorDemo threadExecutorDemo = new ThreadExecutorDemo();

        /**
         * <newFixedThreadPool>
         * 核心线程数=最大线程数，且keepAliveTime=0，即达到核心线程数后不会再创建新线程，
         * 即线程数是固定的，所以也称为fixedThreadPool，适合线程数比较固定的场景
         */
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

        /**
         * <newSingleThreadExecutor>
         * 本质是nThread=1的fixedThreadPool，适合串行
         * 假设有些工作如果多线程并发进行时，会产生一些问题，如死锁等，就适合用newSingleThreadExecutor
         * 池里只有一个在工作，其余在阻塞队列等待
         */
        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();

        /**
         * <newCachedThreadPool>
         * 核心线程数为0，最大线程数为Integer.MAX_VALUE，过期时间60s。即所有线程空闲时间超过60s就会被回收。
         * newCachedThreadPool一般配合SynchronousQueue使用。因为当线程数达到核心线程数后，任务是会放到BlockingQueue的，然后当queue满了，才会创建新线程去处理
         * 但newCachedThreadPool不存在核心线程，任务到来，马上就会被放到queue里，这样的话，queue的大小就没什么意义了，
         * 因为queue的意义就是避免新线程创建，尽量复用核心线程数，而这里怎样都会创建新的线程去处理
         * ps:SynchronousQueue不存储元素，必须要有线程去pull，才能往里面放任务
         *
         */
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        /**
         * <newScheduledThreadPool>
         *  底层是创建一个核心线程数为n，最大线程数为MAX，阻塞队列用DelayedWorkQueue的ThreadPoolExecutor
         *
         */
//        threadExecutorDemo.scheduledTest();
//        threadExecutorDemo.scheduleAtFixedRateTest();
        testBatchSchedule();

        /**
         * BlockingQueue<Runnable>
         * newFixedThreadPool、newSingleThreadExecutor默认使用LinkedBlockingQueue作为阻塞队列，
         * 但newSingleThreadExecutor是无界队列，可能会耗尽资源，所以尽量用有界队列。
         */

        /**
         * <RejectedExecutionHandler>
         * 默认实现是AbortPolicy，中断并抛出reject异常
         * DiscardPolicy，空实现，什么都不做，即抛弃所有后来的任务，而且不抛异常
         * DiscardOldestPolicy，丢弃最旧的任务，让新来的任务得到运行
         * CallerRunsPolicy，直接在当前线程运行新任务，当前一般是主线程，所以很少这样用
         */

        /**
         * <ThreadFactory>
         * 每当线程池需要创建一个新线程，都是通过线程工厂获取。
         * 如果不为ThreadPoolExecutor设定一个线程工厂，就会使用默认的<defaultThreadFactory>
         * 由defaultThreadFactory创建的线程优先级都是NORMAL,而且都是非守护线程
         * 可以实现ThreadFactory自定义线程工厂
         */

        /**
         * <Worker>线程池是通过Worker类去执行任务，Worker继承了AbstractQueuedSynchronizer*
         * （看thread-pool-worker图，描述了<execute>执行后，Worker在线程池中四种情况）- 主要是拿workCount（工作中线程数）与coreSize和maxSize比较，还有看workQueue是否已满等
         * 线程池启动后，Worker调用ThreadFactory创建线程，包装了提交过来的Runnable对象并执行，执行完就等待下一个任务到来（循环调用getTask()），
         * 如果是空闲的非核心线程，过了keepAliveTime就会结束
         *
         * ps:
         * AbstractQueuedSynchronizer，简称AQS，是Java并发包里一系列同步工具的基础实现，
         * 原理是根据状态位来控制线程的入队阻塞、出队唤醒来处理同步。
         */

        /**
         * <FutureTask> 执行Callable任务（有返回结果的任务），可由线程池<submit>方法接收
         * FutureTask构造函数接收一个Callable任务
         * FutureTask的状态路径
         * NEW -> COMPLETING -> NORMAL 正常的流程
         * NEW -> COMPLETING -> EXCEPTIONAL 异常的流程
         * NEW -> CANCELLED 被取消流程
         * NEW -> INTERRUPTING -> INTERRUPTED 被中断流程
         *
         * FutureTask实现了Runnable接口，执行任务时调用run方法，里面会调用callable对象的call方法，并获得结果，set到outcome字段
         * 客户端调用FutureTask的get方法，会阻塞等待，主要是<awaitDone>方法，主要逻辑是：
         * 1、判断任务状态是否已经完成，是就直接返回；
         * 2、任务状态是COMPLETING，代表在set结果时被阻塞了，这里先让出资源-yield
         * 3、如果WaitNode为空，就为当前线程初始化一个WaitNode；
         * 4、如果当前的WaitNode还没有加入waiters，就加入；
         * 5、如果是限定时间执行，判断有无超时，超时就将waiter移出，并返回结果，否则阻塞一定时间；
         * 6、如果没有限定时间，就一直阻塞到下次被唤醒。
         *
         * ps: submit方法执行时，其实也是执行了execute方法，因为FutureTask也是个Runnable对象
         */

        /**
         * <线程池关闭>
         * shutdown: 不能再提交任务，已经提交的任务可继续运行(soft way)
         * shutdownNow: 不能再提交任务，已经提交但未执行的任务不能运行，在运行的任务可继续运行，但会被中断，<返回已经提交但未执行的任务>(hard way)
         * awaitTermination: 阻塞直到线程池Terminated了，一般会先调用shutdown，因为调用shutdown后可能有线程还没执行完
         * 阻塞一定时间后，线程池都没关闭成功的话，则可以采取一定措施
         */


    }

    /**
     * <schedule>，可接收Callable、Runnable对象，间隔一定时间执行任务
     */
    private void scheduledTest() throws ExecutionException, InterruptedException {
        ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(5);
        for (int i = 0; i < 5; i++) {
            Future<String> future = newScheduledThreadPool.schedule(new Callable<String>() {
                @Override
                public String call() {
                    return String.format("thread %s call", Thread.currentThread().getName());
                }
            },2,TimeUnit.SECONDS);
            System.out.println("thread " + Thread.currentThread().getName() + ": " + future.get());
        }
        newScheduledThreadPool.shutdown();

    }

    /**
     * scheduleAtFixedRate: 如果上一个任务的执行时间大于等待时间，任务结束后，下一个任务马上执行
     * scheduleWithFixedDelay: 如果上个任务的执行时间大于等待时间，任务结束后也会等待相应的时间才执行下一个任务
     */
    private void scheduleAtFixedRateTest() {
        ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(5);
        AtomicInteger shutdownCount = new AtomicInteger(3);
        newScheduledThreadPool.scheduleWithFixedDelay(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                System.out.println("thread " + Thread.currentThread().getName() + " call");
//                TimeUnit.SECONDS.sleep(4);// 任务时长超过了period，会等上一个执行完再执行下一个
                if (shutdownCount.decrementAndGet() <= 0) {
                    newScheduledThreadPool.shutdown();
                }
            }
        }, 1, 2, TimeUnit.SECONDS);
    }

    @SneakyThrows
    private static void testBatchSchedule() {
        ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(5);
        ExecutorService executor = Executors.newFixedThreadPool(5);
        int count = 100;
        while (count > 0) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    // 延迟执行某任务
                    scheduledExecutor.schedule(new OrderTask(Thread.currentThread().getId(), new Date()), 5, TimeUnit.SECONDS);
                }
            });
            count--;
//            Random random = new Random();
//            TimeUnit.SECONDS.sleep(random.nextInt(3));
        }
        executor.shutdown();
    }

    @Data
    static class OrderTask implements Runnable{

        public OrderTask(Long orderId, Date orderTime) {
            this.orderId = orderId;
            this.orderTime = orderTime;
        }

        private Long orderId;

        private Date orderTime;

        @Override
        public void run() {
            Date cancelTime = new Date();
            System.out.println(orderId + " " + orderTime + "~" + cancelTime);
        }
    }
}
