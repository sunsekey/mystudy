package com.sunsekey.practise.concurrent._threadlocal;

import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * ThreadLocal:线程变量，为变量在每个线程中都创建了一个副本，get总是返回由当前执行线程在调用set时设置的最新值
 * 这里要注意，threadLocal并不是为了保证线程安全的，如果存value 本身是可以被其他线程修改的，就算用threadLocal也无补于事
 * （因为它底层并不是类似ThreadLocal.Map<Thread,value>的实现，而是Thread.ThreadLocalMap.Entry<ThreadLocal,value>）
 * <ThreadLocal的作用更多是>
 * 我需要用到某个对象o，这个对象o在这个线程A之内会被多处调用，而我不希望将这个对象o当作参数在多个方法之间传递
 * 则可以将这个对象o放到TheadLocal中
 *
 * 底层：
 * 实际上，每个Thread都有一个变量<ThreadLocalMap>，这个映射表的 key 是ThreadLocal实例本身，value 是真正需要存储的 Object
 * ThreadLocal 本身并不存储值，它只是作为一个 key 来让线程从 ThreadLocalMap 获取 value，且 ThreadLocalMap 使用 ThreadLocal 的弱引用作为 key
 *
 *
 * 问题：
 * 可能会导致内存泄漏··
 * 分析：
 * 1）ThreadLocalMap使用ThreadLocal的<弱引用>作为key，gc时，弱引用被回收，key就会变成null
 * 这样一来，ThreadLocalMap中就会出现key为null的Entry，就没有办法访问这些key为null的Entry的value
 * 存在一条强引用链：Thread Ref -> Thread -> ThreaLocalMap -> Entry -> value永远无法回收，造成内存泄漏
 * jdk的解决办法：
 * 在get\set\remove时都会去清除key为null的entry，但如果分配了threadLocal却一直不使用，就会导致内存泄漏
 *
 * 2）为什么不用强引用，如果用强引用，引用ThreadLocal的对象被回收了，但只要Thread还在，ThreadLocalMap就还会持有ThreadLocal的强引用
 * （简单说就是就算ThreadLocal没有了，只要Thread还没"结束"，ThreadLocal也无法被回收，最终导致内存泄漏）
 *
 * 3）而用弱引用，虽然会有key为null的情况，但至少保证了在调用get\set\remove时会被清掉
 *
 * 4) 所以内存泄漏的根源是<ThreadLocalMap的生命周期跟Thread一样长>，而不是因为使用了弱引用
 *
 * 最佳实践
 * 每次用完threadLocal，都手动调用一下remove
 *
 * 参考：https://blog.csdn.net/h2604396739/article/details/83033302
 *
 *
 */
public class ThreadLocalDemo {

    public static void main(String[] args) throws InterruptedException {
        ThreadLocal<MyClassForThreadLocal> myThreadLocal = new ThreadLocal<>();
        // 复写initialValue方法，则不先set也可以get，否则会报空指针
        ThreadLocal<Integer> myIntLocal = ThreadLocal.withInitial(() -> 0);
        Runnable task = ()->{
            MyClassForThreadLocal myClassForThreadLocal = new MyClassForThreadLocal(Thread.currentThread().getId(), Thread.currentThread().getName());
            myThreadLocal.set(myClassForThreadLocal);
            System.out.println("Thread " + Thread.currentThread().getName() + " get data: " + myThreadLocal.get() + " and int value: " + myIntLocal.get());
        };
        for (int i = 0; i < 10; i++) {
            new Thread(task).start();
            TimeUnit.MILLISECONDS.sleep(500);
        }
        System.out.println(myThreadLocal);
        myThreadLocal.remove();
    }

    @Data
    public static class MyClassForThreadLocal{
        private Long l;
        private String str;


        public MyClassForThreadLocal(Long l, String str) {
            this.l = l;
            this.str = str;

        }

        @Override
        public String toString() {
            return "l: " + l + " str: " + str;
        }
    }
}
