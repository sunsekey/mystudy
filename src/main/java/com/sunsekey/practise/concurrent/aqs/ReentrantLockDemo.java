package com.sunsekey.practise.concurrent.aqs;

import lombok.Data;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <ReentrantLock>实现了Lock接口，内部持有一个AQS实现。<双向队列、state、waitState等都定义在AQS>
 * 和synchronized相比，ReentrantLock用起来会复杂一些。
 * 在基本的加锁和解锁上，两者是一样的，所以无特殊情况下，推荐使用synchronized。
 * ReentrantLock的优势在于它更灵活、更强大，除了常规的lock()、unlock()之外，还有lockInterruptibly()、tryLock()方法，支持中断、超时。
 *
 * 重入锁：支持重进入的锁，它表示该锁能够支持一个线程对资源的重复加锁。
 * （村民打水的故事，重入就相当于同一家人的人过来了，参考：https://www.cnblogs.com/gxyandwmm/p/9387833.html）
 * ReentrantLock和synchronized都是可重入的。synchronized因为可重入因此可以放在被递归执行的方法上,且不用担心线程最后能否正确释放锁，
 * 而ReentrantLock在重入时要却<确保重复获取锁的次数必须和重复释放锁的次数一样>，否则可能导致其他线程无法获得该锁。
 *
 * ReentrantLock的内部类Sync继承了AQS，分为公平锁FairSync和非公平锁NonfairSync
 * 公平锁：线程获取锁的顺序和调用lock的顺序一样，FIFO；（队列）
 * -获取锁，调用lock方法：
 * 1）调用AQS的acquire()。线程尝试去获得锁，
 * 1.1)如果看到state=0(没有人占用"锁")，
 *      那么再判断队列的中是否已经在等待锁且是比当前线程等得久的线程(hasQueuedPredecessors())，
 *      没有的话则CAS更改state to 1
 * 1.2）如果state!=0，则判断exclusiveOwnerThread（这个变量的语义是排他模式下拥有资源的线程）是否等于当前线程，是的话，state + 1
 * 1.3）以上都不是，则返回false，尝试获取"锁"失败
 *
 * 2）1中失败的话，则将线程包装成Node(addWaiter())，加入到队列中去（AQS内部维护一条<双向队列>）。
 * 这里逻辑比较简单，如果队列为空，则先为头节点创建一个空的Node（头节点代表获取了锁的线程，现在还没有，所以先空着）
 * 如果队列不为空，则死循环进行CAS，插入到队列里
 * 3）插入到队列后，则去阻塞线程(acquireQueued())。进入一个死循环，
 * 3.1）先会判断下自己的前一个节点是不是头节点，是的话，就再次去尝试获得"锁"（tryAcquire）,成功的话，自己就变成头节点
 * 3.2）下一位不是头节点或者再次尝试获取锁没能成功的话，根据<前节点的状态>决定自己是否需要被阻塞(shouldParkAfterFailedAcquire())
 * （Node的<waitState>变量描述了线程的等待状态:
 * CANCELLED(1)-取消 SIGNAL(-1)-下个节点需要被唤醒 CONDITION(-2)-线程在等待条件触发 PROPAGATE(-3)-（共享锁）状态需要向后传播）
 * - 如果前节点（pn）的状态是SIGNAL，那么当前节点(cn)线程需阻塞，实际是调用了LookSupport.park()进行阻塞
 * - 如果pn状态是CANCELLED，则循环将前面所有CANCELLED的节点一并移出队列，且cn线程不需要被阻塞，继续抢锁
 * - 如果pn是其他状态，那么CAS将pn的waitState修改为SIGNAL，且cn线程不需要被阻塞
 * 3.3）如果cn（当前节点）线程需要被阻塞，则调用LockSupport.park(this)进行阻塞（类似wait()，对应unPark()）-- parkAndCheckInterrupt()
 *
 *
 * -释放锁，调用unlock方法：
 * 1）调用AQS的release()。release调用tryRelease()。因为每次lock会让state加1，对应地每次unlock要让state减1，直到为0时将独占线程变量设置为空（setExclusiveOwnerThread(null))，返回true（没减到0则返回false）
 * 2）调用unParkSuccessor()将头节点的下个节点唤醒，下个节点被唤醒后会去执行3.1的逻辑尝试获取锁
 *
 * -中断锁，调用lock而park的线程，有两种被唤醒的可能
 * 1）前节点是头节点，释放锁后，会调用LockSupport.unpark唤醒当前线程。
 * 整个过程没有涉及到中断，最终acquireQueued返回false时，不需要调用selfInterrupt。
 * 2）LockSupport.park支持响应中断请求，能够被其他线程通过interrupt()唤醒。
 * 但这种唤醒并没有用，因为线程前面可能还有等待线程，在acquireQueued的循环里，线程会再次被阻塞。
 * parkAndCheckInterrupt返回的是Thread.interrupted()，不仅返回中断状态，还会清除中断状态，保证阻塞线程忽略中断。
 * 最终acquireQueued返回true时（循环中成功获得了锁而返回，但曾经被中断过，虽然这种中断唤醒没有用，但真正的中断状态被清除了//todo 这样有什么影响？导致线程之后会忽略中断请求？），
 * 需要调用selfInterrupt维持中断状态（自身线程调interrupt）。
 *
 * 非公平锁：线程获取锁的顺序和调用lock的顺序无关，全凭运气。"新来的线程不会乖乖排队"。(性能更好，因跳过了对队列的处理，ReentrantLock默认使用非公平锁)
 * -获取锁：
 * 1）调用lock后先直接CAS抢锁，如果成功，直接就获得锁；失败则继续尝试获取锁。如下：
 * if (compareAndSetState(0, 1))
 *         setExclusiveOwnerThread(Thread.currentThread());
 *     else
 *         acquire(1);
 *
 * 2）进入tryAcquire后，相比公平锁，只是少了调用hasQueuedPredecessors()，即不用理会队列是否有排在前面的线程在等待锁，直接就继续去获取"锁"
 *
 * ps:
 * 1、这里的"锁"实则是指state的修改权
 * 2、大部分情况下我们使用非公平锁，因为其性能比公平锁好很多。但是公平锁能够避免线程饥饿，某些情况下也很有用。
 *
 * 除了lock、unlock，相比synchronized新增的方法：
 * <lockInterruptibly()>这个方法会优先考虑中断，而不是响应锁的普通获取或重入获取。
 * 和lock()不同在于，doAcquireInterruptibly()（对应调用lock时的acquireQueued()）中，
 * 如果被中断唤醒，不再是interrupted = true然后继续去循环获取锁，而是throw new InterruptedException();直接中断
 *
 *
 *
 */
public class ReentrantLockDemo {

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockDemo reentrantLockDemo = new ReentrantLockDemo();
        reentrantLockDemo.MyLinkedQueueTest();

    }

    /**
     * 开启一个死锁任务
     *
     */
    private void startDeadTasks() {
        Lock lock1 = new ReentrantLock();
        Lock lock2 = new ReentrantLock();

        // 模拟两条线程以相反顺序申请资源，即制造了循环等待的条件（死锁条件之一）
        Thread thread1 = new Thread(new DeadTask(lock1, lock2));
        Thread thread2 = new Thread(new DeadTask(lock2, lock1));

        thread1.start();
        thread2.start();

//         要手动中断其中一个线程，但这样解决死锁不优雅
//        thread1.interrupt();
    }

    /**
     * 模拟死锁场景
     */
    private void deadLockDemo() {
        startDeadTasks();
    }

    /**
     * 死锁任务
     */
    @Data
    public static class DeadTask implements Runnable {

        private Lock firstLock;
        private Lock secondLock;

        DeadTask(Lock firstLock, Lock secondLock) {
            this.firstLock = firstLock;
            this.secondLock = secondLock;
        }

        @SneakyThrows
        @Override
        public void run() {
            try {
                while (!firstLock.tryLock()) {
                    System.out.println("Thread " + Thread.currentThread().getName() + " get first lock " + firstLock + " fail");
                    TimeUnit.MILLISECONDS.sleep(10);
                }
                System.out.println("Thread " + Thread.currentThread().getName() + " locked with the first lock-" + firstLock);
                while (!secondLock.tryLock()) {
                    System.out.println("Thread " + Thread.currentThread().getName() + " get second lock " + secondLock + " fail");
                    firstLock.unlock();
                    TimeUnit.MILLISECONDS.sleep(10);
                }
                System.out.println("Thread " + Thread.currentThread().getName() + " locked with the second lock-" + secondLock);
            } finally {
                System.out.println("Thread " + Thread.currentThread().getName() + " ready to unlock");
                try {
                    firstLock.unlock();// 多次unlock会报错..
                    secondLock.unlock();
                } catch (Exception e) {

                }
                System.out.println("Thread " + Thread.currentThread().getName() + " finished");
            }

//            try {
//                firstLock.lockInterruptibly();
//                System.out.println("Thread " + Thread.currentThread().getName() + " locked with the first lock-" + firstLock);
//                TimeUnit.MILLISECONDS.sleep(10);
//                System.out.println("Thread " + Thread.currentThread().getName() + " ready to lock with the second lock-" + secondLock);
//                secondLock.lockInterruptibly();
//            } catch (InterruptedException e) {
//
//            }finally {
//                System.out.println("Thread " + Thread.currentThread().getName() + " ready to unlock");
//                firstLock.unlock();
//                secondLock.unlock();
//                System.out.println("Thread " + Thread.currentThread().getName() + " finished");
//            }
        }
    }

    /**
     * 公平锁和非公平锁演示
     */
    private void fairLockTest() {
        ReentrantLock lock = new ReentrantLock(false);

        Runnable task = () -> {
            for (int i = 0; i < 2; i++) {
                /**
                 * 公平下：
                 * thread 0~4依次start，然后依次调用lock()，依次竞争锁
                 * thread0先争夺到锁，然后thread1~4依次排队，所以最后输出0、1、2、3、4、0、1、2、3、4
                 *
                 * 不公平下：
                 * 不会按顺序，输出结果是0、0、1、1、2、2、3、3、4、4。
                 * 为什么"看起来"还是有规律，而不是想象中的杂乱无章的排序呢？因为第一条线程unlock后，进入第二次循环，马上又lock了，即还是抢在其他线程前面了
                 * 如果把sleep放在unlock之后，输出结果就是乱的了
                 */
                lock.lock();
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("获得锁的线程：" + Thread.currentThread().getName());
                lock.unlock();
            }
        };
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(task);
            thread.setName("thread " + i);
            threadList.add(thread);
        }
        for (Thread thread : threadList) {
//            System.out.println("thread " + thread.getName() + " start!");
            thread.start();
        }

    }

    /**
     * 演示配合Condition使用
     */
    private void conditionDemo() throws InterruptedException {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        lock.lock();
        Runnable signalTask = () -> {
            System.out.println("唤醒线程");
            lock.lock();// 主线程已获得锁，这个是另外一条线程，不能获得锁，所以阻塞着
            try{
                condition.signal();
            }finally {
                System.out.println("唤醒完，释放锁");
                lock.unlock();
            }
        };
        new Thread(signalTask).start();
        try{
            System.out.println("主线程获得锁，然后等待被唤醒");
            condition.await();// await操作，会释放锁进入等待阶段，子线程可以获得锁，然后进行signal了
        }finally {
            System.out.println("被唤醒了，释放锁");
            lock.unlock();
        }

    }

    /**
     * 自定义阻塞队列-测试
     * @throws InterruptedException
     */
    private void MyLinkedQueueTest() throws InterruptedException {
        // ps: 队列长度设为2，以10个线程并发插入数据，如果一直没有出列的命令，那么剩余8个元素会一直阻塞着，等待notFull的条件满足为止
        MyBlockingQueue<Integer> queue = new MyBlockingQueue<>(2);
        for (int i = 0; i < 10; i++) {
            int data = i;
            new Thread(() -> queue.enqueue(data)).start();
        }
        for (int i = 0; i < 10; i++) {

            new Thread(() -> {
                Integer data = queue.dequeue();
                System.out.println("dequeue data: " + data);
            }).start();
        }
    }

    /**
     * 演示Condition实现队列
     */
    @Data
    public static class MyBlockingQueue<T>{
        public MyBlockingQueue(int size) {
            this.size = size;
        }
        private int size = 0;
        private LinkedList<T> linkedList = new LinkedList<>();

        Lock lock = new ReentrantLock();
        Condition notFull = lock.newCondition();
        Condition notEmpty = lock.newCondition();

        public void enqueue(T item) {
            lock.lock();
            try{
                while (linkedList.size() == size) {
                    notFull.await();// 等待队列不满的信号
                }
                linkedList.add(item);
                notEmpty.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public T dequeue() {
            lock.lock();
            T removeItem = null;
            try{
                while (linkedList.isEmpty()) {
                    notEmpty.await();
                }
                removeItem = linkedList.removeFirst();
                notFull.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
            return removeItem;
        }
    }
}
