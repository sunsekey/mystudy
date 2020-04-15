package com.sunsekey.practise.concurrent.lock;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

/**
 * 内存可见性：同步块的可见性是由“如果对一个变量执行lock操作，将会清空<工作内存>中此变量的值，在执行引擎使用这个变量前需要重新执行<load或assign>操作初始化变量的值”、
 * “对一个变量执行unlock操作之前，必须先把此变量同步回主内存中（<执行store和write操作>）”这两条规则获得的。
 * 操作原子性：持有同一个锁的两个同步块只能串行地进入。
 * <p>
 * 独占锁：是一种悲观锁，synchronized就是一种独占锁，会<导致其它所有需要锁的线程挂起>，等待持有锁的线程释放锁。
 * <p>
 * 乐观锁：每次不加锁，假设没有冲突去完成某项操作，如果因为冲突失败就重试，直到成功为止。
 * <p>
 * Synchronized（重量级锁）
 * Synchronized是通过对象内部的一个叫做监视器锁（monitor）来实现的，
 * 监视器锁本质又是依赖于底层的<操作系统>的Mutex Lock（互斥锁）来实现的。
 * Mutex Lock（互斥锁）
 * 每个对象都对应于一个可称为" 互斥锁" 的标记，这个标记用来保证在任一时刻，只能有一个线程访问该对象。
 * <p>
 * 操作系统实现线程之间的切换需要从<用户态转换到核心态>，这个成本非常高，状态之间的转换需要相对比较长的时间。
 * 因此，这种依赖于操作系统Mutex Lock所实现的锁我们称之为“重量级锁”。
 * <p>
 * 偏向锁：在只有同一个线程执行同步块时提高性能。避免了进出同步块时不需要进行CAS操作来加锁和解锁，只需简单比较ThreadID。
 * 只有等到线程竞争出现才释放偏向锁，持有偏向锁的线程不会主动释放偏向锁。
 * 之后的线程竞争偏向锁，会先检查持有偏向锁的线程是否存活，如果不存活，则对象变为无锁状态，重新偏向（即新来的线程获得偏向锁）；
 * 如果仍存活，则偏向锁升级为轻量级锁，此时轻量级锁由原持有偏向锁的线程持有，继续执行其同步代码，而正在竞争的线程会进入自旋等待获得该轻量级锁
 * <p>
 * 轻量级锁：若当前只有一个等待线程，则可通过自旋稍微等待一下，可能持有轻量级锁的线程很快就会释放锁。
 * 但是当自旋超过一定的次数，或者一个线程在持有锁，一个在自旋，又有第三个来访时，轻量级锁膨胀为重量级锁
 * <p>
 * 重量级锁：等待锁的线程会被阻塞，所以涉及到用户态和内核态的切换、操作系统内核态中的线程的阻塞和恢复。
 * <p>
 * 锁消除：JVM编译时发现同步块中不涉及共享资源，那么就会将锁消除
 * <p>
 * 锁粗化：JVM检测到有一串零碎的操作都是对同一对象的加锁，将会把加锁同步的范围扩展
 * <p>
 * 自旋锁：共享数据的锁定状态只会持续很短一段时间，为了这一段很短的时间频繁地阻塞和唤醒线程非常不值得。
 * 让该线程执行一段无意义的忙循环（自旋）等待一段时间，不会被立即挂起（自旋不放弃处理器执行时间），看持有锁的线程是否会很快释放锁。
 * 自旋锁在JDK 1.4.2中引入，默认关闭，但是可以使用-XX:+UseSpinning开开启；在JDK1.6中默认开启。
 * 自旋等待不能替代阻塞，虽然它可以避免线程切换带来的开销，但是它占用了处理器的时间。如果持有锁的线程很快就释放了锁，那么自旋的效率就非常好；
 * 反之，自旋的线程就会白白消耗掉处理器的资源，它不会做任何有意义的工作，这样反而会带来性能上的浪费。默认自旋10次，如果自旋超过了定义的时间仍然没有获取到锁，则应该被挂起（进入阻塞状态）
 * <p>
 * 自适应自旋锁：JDK1.6引入自适应的自旋锁，自适应就意味着自旋的次数不再是固定的。简单来说，就是线程如果自旋成功了，则下次自旋的次数会更多（因为有成功过，值得多等待一下），
 * 如果自旋失败了，则下次自旋的次数就会减少。
 * <p>
 * 其他：
 * 由于JDK1.6中加入了针对锁的优化措施，使得synchronized与ReentrantLock的性能基本持平。
 * ReentrantLock只是提供了synchronized更丰富的功能，而不一定有更优的性能，所以<在synchronized能实现需求的情况下，优先考虑使用synchronized来进行同步>
 *
 * synchronized用法：<对象锁，类锁、方法锁>（具体看下面例子）
 * 1、对象锁：对象锁：自定义对象。synchronized(对象)可以理解为，一个线程去问object要锁，
 * 同时，其他线程也在问同一个object要锁，那么就会产生阻塞。
 * 但如果不同线程问的(object)对象不是同一个对象，那么就不会产生阻塞
 * 2、类锁：即线程到达同步方法或同步块时，向类去拿锁，但一个类只有一把锁，每个线程只能同步去拿
 * 3、方法锁：即在方法上加锁，加在静态方法的话即类锁，普通方法则对象锁(synchronized(this))
 */
public class SynchronizedDemo {

    public static void main(String[] args) {
        SynchronizedDemo synchronizedDemo = new SynchronizedDemo();
//        synchronizedDemo.synchronizedThisTest();
//        synchronizedDemo.synchronizedObjectTest();
        synchronizedDemo.classSynchronizedTest();
    }

    /**
     * 对象锁：this
     */
    public void synchronizedThisTest() {
        MySynchronizedThisTask task1 = new MySynchronizedThisTask();
        // 注意！多条线程访问的对象如果不是同一个，加对象锁也是没用的。
        // thread1拿到task1的锁，thread2拿到task2的锁，互不干扰，达不到同步效果
//        MyTask task2 = new MyTask();
//        Thread thread2 = new Thread(task2);
        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task1);
        thread1.start();
        thread2.start();
    }

    public static class MySynchronizedThisTask implements Runnable {

        @SneakyThrows
        @Override
        public void run() {
            /**
             * 这种写法相当于在方法签名上加synchronized，即方法锁
             */
            synchronized (this){
                System.out.println("Thread " + Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(2);
                System.out.println("Thread " + Thread.currentThread().getName() + " finish");
            }
        }
    }

    /**
     * 对象锁：自定义对象。
     */
    private void synchronizedObjectTest() {
        MySynchronizedObjectTask task = new MySynchronizedObjectTask();
        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);
        thread1.start();
        thread2.start();
    }

    public static class MySynchronizedObjectTask implements Runnable {
        private final Object object1 = new Object();
        @SneakyThrows
        @Override
        public void run() {
//            Object object1 = new Object(); 这样的话，每个线程进来都会创建一个object，然后分别都问这个新object拿锁，那肯定都可以拿到，所以线程间就不会同步
            synchronized (object1){
                System.out.println("Thread " + Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(2);
                System.out.println("Thread " + Thread.currentThread().getName() + " finish");
            }
        }
    }

    /**
     * 类锁
     */
    private void classSynchronizedTest() {
        MyClassSynchronizedTask task1 = new MyClassSynchronizedTask();
        MyClassSynchronizedTask task2 = new MyClassSynchronizedTask();
        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);
        thread1.start();
        thread2.start();
    }

    public static class MyClassSynchronizedTask implements Runnable{

        @SneakyThrows
        @Override
        public void run() {
//            printSomething();
            synchronized (SynchronizedDemo.class){
                System.out.println("Thread " + Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(2);
                System.out.println("Thread " + Thread.currentThread().getName() + " finish");
            }

        }
    }

    private synchronized static void printSomething() throws InterruptedException {
        System.out.println("Thread " + Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(2);
        System.out.println("Thread " + Thread.currentThread().getName() + " finish");
    }

}
