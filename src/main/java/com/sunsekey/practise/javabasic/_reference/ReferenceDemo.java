package com.sunsekey.practise.javabasic._reference;

import lombok.Data;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * 四种引用：强引用、软引用、弱引用、虚引用
 * 参考：https://juejin.im/post/5b82c02df265da436152f5ad#comment
 *      https://www.cnblogs.com/dolphin0520/p/3784171.html
 */
public class ReferenceDemo {

    public static void main(String[] args) throws InterruptedException {
//        strongReferenceTest();
//        softReferenceTest();
//        weakReferenceTest();
        phantomReferenceTest();
    }

    /**
     * 强引用
     * Jvm不会去回收，即使抛OOM
     */
    public static void strongReferenceTest() {
        Fish[] fishes = new Fish[4];
        for (int i = 0; i < fishes.length; i++) {
            fishes[i] = new Fish("fish- " + i);
        }
        for (int i = 0; i < fishes.length; i++) {
            fishes[i] = null; // todo 按执行结果看 应该直接fishes = null;也可，需考证
        }
    }

    /**
     * 软引用：
     * 软引用是用来描述一些有用但并不是必需的对象
     * 空间不足时会被回收，空间充足时就不会被回收（对于"比较新"的软引用对象，可能也不一定会被马上回收）
     * 使用场景：很适合用来实现缓存：比如网页缓存、图片缓存等
     */
    public static void softReferenceTest() {
        ReferenceQueue<Fish> referenceQueue = new ReferenceQueue<>();
        WeakReference<Fish> weakFishRef = new WeakReference<>(new Fish("soft-fish"),referenceQueue);
        System.out.println(weakFishRef.get());
        // 假设内存不足...

        System.gc();                //通知JVM的gc进行垃圾回收，gc不一定会马上执行
        System.out.println(weakFishRef.get());
    }

    /**
     * 弱引用
     * 弱引用也是用来描述非必需对象的，区别是对象拥有更短暂的生命周期
     * 当JVM进行垃圾回收时，无论内存是否充足，都会回收被弱引用关联的对象
     *
     * 场景：如果一个对象是偶尔(很少)的使用，并且希望在使用时随时就能获取到，但又不想影响此对象的垃圾收集，
     * 那么你应该用Weak Reference来记住此对象。
     *
     * @throws InterruptedException
     */
    public static void weakReferenceTest() throws InterruptedException {
        ReferenceQueue<Fish> referenceQueue = new ReferenceQueue<>();
        WeakReference<Fish> weakFishRef = new WeakReference<>(new Fish("weak-fish"),referenceQueue);
        System.out.println(weakFishRef.get());
        System.gc();                //通知JVM的gc进行垃圾回收
        System.out.println(weakFishRef.get());
    }

    /**
     * 虚引用
     * 一个对象与虚引用关联，则跟没有引用与之关联一样，在任何时候都可能被垃圾回收器回收
     * 和弱引用不同，虚引用必须和引用队列(ReferenceQueue)联合使用。
     * 1）当垃圾回收器准备回收一个对象时，如果发现它还有虚引用，就会在回收对象的内存之前，把这个虚引用加入到与之关联的引用队列中。
     * 2）如果程序发现某个虚引用已经被加入到引用队列，那么就可以在所引用的对象的内存被回收之前采取必要的行动
     * 场景：虚引用主要用来跟踪对象被垃圾回收器回收的活动
     */
    public static void phantomReferenceTest() {
        ReferenceQueue<Fish> referenceQueue = new ReferenceQueue<>();
        PhantomReference<Fish> phantomFishRef = new PhantomReference<>(new Fish("phantom-fish"), referenceQueue);
        System.out.println(phantomFishRef.get());// must be null
        System.gc();// 通知JVM的gc进行垃圾回收

    }

    @Data
    static class Fish{

        private String name;
        public Fish(String name) {
            this.name = name;
        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            System.out.println("fish " + name + " finalized");
        }
    }
}
