package com.sunsekey.practise.io.aio;

import java.util.Scanner;

/**
 * AIO
 * 真正的异步非阻塞I/O，对应于UNIX网络编程中的事件驱动I/O（AIO）。
 * 优点：
 * 1）它不需要对注册的通道进行轮询即可实现异步读写，从而简化了NIO的编程模型
 * 2）它不是在IO准备好时再通知线程，而是在IO操作已经完成后，再给线程发出通知
 */
public class AIOTest {

    public static void main(String[] args) throws Exception {
        //测试主方法
        //运行服务器
        AIOServer.start();
        //避免客户端先于服务器启动前执行代码
        Thread.sleep(100);
        //运行客户端
        AIOClient.start();
        System.out.println("请输入请求消息：");
        Scanner scanner = new Scanner(System.in);
        while (AIOClient.sendMsg(scanner.nextLine())) ;
    }
}

