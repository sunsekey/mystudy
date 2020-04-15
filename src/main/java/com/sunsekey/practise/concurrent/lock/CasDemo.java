package com.sunsekey.practise.concurrent.lock;

import lombok.SneakyThrows;

import java.util.concurrent.atomic.AtomicReference;

/**
 * CAS，compare and swap，不加锁，假设没有冲突去完成某项操作，如果因为冲突失败就重试，直到成功为止。整个J.U.C(java.util.concurrent)都是建立在CAS上（如AtomicInteger，底层利用CPU的CAS指令），对于synchronized阻塞算法，J.U.C在性能上有了很大的提升
 * 优点：
 * 在轻度到中度的争用情况下，非阻塞算法的性能会超越阻塞算法，
 * 因为 CAS 的多数时间都在第一次尝试时就成功，而发生争用时的开销也不涉及线程挂起和上下文切换，只多了几个循环迭代
 * 而在高度争用的情况下（即有多个线程不断争用一个内存位置的时候），基于锁的算法开始提供比非阻塞算法更好的吞吐率，
 * 因为当线程阻塞时，它就会停止争用，耐心地等候轮到自己，从而避免了进一步争用。
 *
 * 原子变量的概念
 * 原子变量能够保证原子性的操作，意思是某个任务在执行过程中，要么全部成功，要么全部失败回滚，
 * 恢复到执行之前的初态，不存在初态和成功之间的中间状态。
 * 例如CAS操作，要么比较并交换成功，要么比较并交换失败。由CPU保证原子性。
 *
 * 通过原子变量可以实现线程安全。执行某个任务的时候，先假定不会有冲突，若不发生冲突，则直接执行成功；
 * 当发生冲突的时候，则执行失败，回滚再重新操作，直到不发生冲突。
 *
 * 缺陷：导致ABA问题
 *
 */
public class CasDemo {

    public static void main(String[] args) throws InterruptedException {

//        CasCounter casCounter = new CasCounter();
        NonBlockingConcurrentStackTest();
    }

    private static void NonBlockingConcurrentStackTest() throws InterruptedException {
        NonBlockingConcurrentStack<Integer> nonBlockingConcurrentStack = new NonBlockingConcurrentStack<>();
        // todo test
        Thread.sleep(6000); // 睡眠等待线程完成
        System.out.println("stack remain " + nonBlockingConcurrentStack.size() +  " nodes");
        nonBlockingConcurrentStack.peekAll();
    }

    /**
     * todo https://www.ibm.com/developerworks/cn/java/j-jtp04186/
     * 实现一个阻塞队列
     */
    public static void linkedQueueTest(){

    }

    /**
     * aba问题
     * https://www.cnblogs.com/549294286/p/3766717.html
     * todo 解决aba问题，运用版本戳AtomicStampedReference/AtomicMarkableReference
     */
    public static void avoidABATest(){

    }

    /**
     * cas模拟计数器
     */
    public static class CasCounter{
        private SimulatedCAS simulatedCAS;// 可用AtomicInteger代替

        public CasCounter() {
            this(0);
        }

        public CasCounter(int value) {
            this.simulatedCAS = new SimulatedCAS(value);
        }

        public int get() {
            return simulatedCAS.getValue();
        }

        public int increment() {
            int v;
            do {
                v = simulatedCAS.getValue();
            } while (v != simulatedCAS.compareAndSwap(v, v + 1));
            // v是旧值，compareAndSwap返回的也是旧值，如果不相等的话，那就是"旧值"已被其他线程更改了，那么继续读-比较-写的操作
            return simulatedCAS.getValue();
        }
        public static class SimulatedCAS{
            // 加上volatile 保证了内存可见性
            private volatile int value;

            public SimulatedCAS(int value) {
                this.value = value;
            }

            public int getValue() {
                return value;
            }

            public int compareAndSwap(int expectedValue, int newValue) {
                int oldValue = value;
                if (oldValue == expectedValue) {
                    value = newValue;
                }
                return oldValue;
            }
        }
    }

    /**
     * cas实现支持并发的非阻塞堆栈
     */
    public static class NonBlockingConcurrentStack<T>{

        AtomicReference<Node<T>> nodeAtomicWrapper = new AtomicReference<>();

        public void push(T item) {
            Node<T> newNode = new Node<>(item);
            Node<T> oldHead;
            do {
                oldHead = nodeAtomicWrapper.get();
                newNode.next = oldHead;
            } while (!nodeAtomicWrapper.compareAndSet(oldHead, newNode));
        }

        @SneakyThrows
        public T pop(boolean isSleep) {
            // 这里栈的最后一个就是head
            Node<T> oldHead;
            Node<T> newHead;
            do {
                oldHead = nodeAtomicWrapper.get();
                if (oldHead == null) {
                    return null;
                }
                newHead = oldHead.next;
                if (isSleep) {
                    System.out.println("before sleep newHead for r1 is " + newHead.t);
                    Thread.sleep(2000);
                    System.out.println("after sleep newHead for r1 is " + newHead.t);
                }
            } while (!nodeAtomicWrapper.compareAndSet(oldHead, newHead));
            return oldHead.t;
        }

        public T pop() {
            return pop(false);
        }

        public void peekAll() {
            System.out.println("here is the stack----------");
            Node<T> node = nodeAtomicWrapper.get();
            while (node != null) {
                System.out.println("|" + node.t + "|");
                node = node.next;
            }
            System.out.println("|_|");
        }

        public int size() {
            Node<T> node = nodeAtomicWrapper.get();
            int count = 0;
            while (node != null) {
                node = node.next;
                count++;
            }
            return count;
        }

        public static class Node<T>{
            private T t;
            private Node<T> next;

            public Node(T item){
                this.t = item;
            }
        }

    }

}
