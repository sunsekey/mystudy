package com.sunsekey.practise.concurrent._volatile;

/**
 * volatile是jvm提供的轻量级同步机制，有两种语义
 * 1）内存可见性。对于一个没有volatile修饰的的共享变量,当一个线程对其进行了修改，另一线程并不一定能马上看见这个被修改后的值，即内存不可见
 * 原理：一条线程修改一个volatile变量时，先修改工作内存（工作内存实际上不存在，只是缓冲区、CPU寄存器等的抽象概念）中该变量的值，
 * 然后刷新主内存该变量的值。另一条线程对该变量进行读操作时，先将工作内存中该变量置为失效，重新从主内存中读取该变量的值
 * （类比缓存失效）
 * 2）指令重排
 *  instance = new MyObject();可以分为以下3步完成（伪代码
 *  1）memory = allocate();    // 分配内存对象空间
 *  2）instance = (memory);   // 初始化对象
 *  3）instance = memory;     // 设置instance指向刚分配的内存地址，此时instance != null
 *      2、3指令可能会被进行重排执行顺序（因为它们之间没有依赖），变成：
 *  1）memory = allocate();    // 分配内存对象空间
 *  3）instance = memory;     // 设置instance指向刚分配的内存地址，此时instance != null
 *  2）instance = (memory);   // 初始化对象
 *  这样程序可能就会拿到没有被初始化的对象。加上volatile声明就可以避免指令重排
 *  （重排序是编译器和CPU自动进行的）
 *
 *  其他特性：
 *  对volatile变量的读写具有<原子性>，但是其他操作并不一定具有原子性，一个简单的例子就是i++。
 *  由于该操作并不具有原子性，故而即使该变量被volatile修饰，多线程环境下也不能保证线程安全。
 *
 *  特点：由于对volatile的写-读与锁的释放-获取具有相同的内存语义，故某些时候可以代替锁来获得更好的性能。
 *  但是和锁不一样，它不能保证任何时候都是线程安全的。
 */
public class VolatileDemo {

}
