package com.sunsekey.practise.io.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, AsyncServerHandler> {

    @Override
    public void completed(AsynchronousSocketChannel channel, AsyncServerHandler serverHandler) {
        AIOServer.clientCount++;
        System.out.println("连接的客户端数：" + AIOServer.clientCount);
        // 继续接受其他客户端的请求（递归..）
        serverHandler.channel.accept(serverHandler, this);
        // 创建新的Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //异步读  第三个参数为接收消息回调的业务Handler
        channel.read(buffer, buffer, new ServerReadHandler(channel));
    }
    @Override
    public void failed(Throwable exc, AsyncServerHandler serverHandler) {
        exc.printStackTrace();
        serverHandler.latch.countDown();
    }
}
