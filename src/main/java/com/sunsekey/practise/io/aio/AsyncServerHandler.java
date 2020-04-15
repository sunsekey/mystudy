package com.sunsekey.practise.io.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

public class AsyncServerHandler implements Runnable {

    public CountDownLatch latch;
    public AsynchronousServerSocketChannel channel;

    public AsyncServerHandler(int port) {
        try {
            // 不用创建selector
            // 创建服务端通道
            channel = AsynchronousServerSocketChannel.open();
            // 绑定端口
            channel.bind(new InetSocketAddress(port));
            System.out.println("服务器已启动，端口号：" + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // CountDownLatch初始化
        // 它的作用：在完成一组正在执行的操作之前，允许当前的现场一直阻塞
        // 此处，让现场在此阻塞，防止服务端执行完成后退出
        // 也可以使用while(true)+sleep
        latch = new CountDownLatch(1);
        // 开启一个异步操作来accept客户端的连接请求，第二个参数是消费结果的handler
        channel.accept(this, new AcceptHandler());
        //..这里可以做其他事情了

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
