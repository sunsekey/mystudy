package com.sunsekey.practise.io.nio;

import java.util.Scanner;

/**
 * NIO
 * NIO提供了与传统BIO模型中的Socket和ServerSocket相对应的SocketChannel和ServerSocketChannel两种不同的套接字通道实现
 * 新增的着两种通道都支持阻塞和非阻塞两种模式
 * 阻塞模式使用就像传统中的支持一样，比较简单，但是性能和可靠性都不好；非阻塞模式正好与之相反
 *
 * 对于低负载、低并发的应用程序，可以使用同步阻塞I/O来提升开发速率和更好的维护性
 * 对于高负载、高并发的（网络）应用，应使用NIO的非阻塞模式来开发
 *
 * 缓冲区 Buffer
 * Buffer是一个对象，包含一些要写入或者读出的数据
 * 在NIO库中，所有数据都是用缓冲区处理的。在读取数据时，它是直接读到缓冲区中的；在写入数据时，也是写入到缓冲区中
 *
 * 通道 Channel
 * 我们对数据的读取和写入要通过Channel，是一个通道。通道不同于流的地方就是通道是双向的，可以用于读、写和同时读写操作
 *
 * Selector
 * 提供选择已经就绪的任务的能力：Selector会不断轮询注册在其上的Channel，如果某个Channel上面发生读或者写事件，
 * 这个Channel就处于就绪状态，会被Selector轮询出来
 *
 * 优点：
 * 1）线程模型优化为1：N（N < 进程可用的最大句柄数）或者 M : N (M通常为 CPU 核数 + 1， N < 进程可用的最大句柄数)
 * 缺点：
 * 编程模型比较复杂，开发和维护成本高
 *
 * 疑问 todo
 * 怎样判定就绪？譬如读操作，一次没读完，在轮询时怎么知道上一次的读操作还没完成？
 *
 * ps:
 *
 *
 * 即虽然NIO在网络操作中，提供了非阻塞的方法，但是NIO的IO行为还是同步的。
 * 对于NIO来说，我们的业务线程是在IO操作准备好时，得到通知，接着就由这个线程<自行进行>IO操作（如回调中，才调用channel.read等），
 * 而IO操作本身是同步的（由操作系统完成的一系列操作，包括将数据从内核态拷贝到用户态等）
 */
public class NioTest {

    //测试主方法
    @SuppressWarnings("resource")
    public static void main(String[] args) throws Exception{
        //运行服务器
        NIOServer.start();
        //避免客户端先于服务器启动前执行代码
        Thread.sleep(100);
        //运行客户端
        NIOClient.start();
        while(NIOClient.sendMsg(new Scanner(System.in).nextLine()));
    }

}
