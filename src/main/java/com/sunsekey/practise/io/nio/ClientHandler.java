package com.sunsekey.practise.io.nio;

import lombok.SneakyThrows;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class ClientHandler implements Runnable {
    private String host;
    private int port;
    private Selector selector;
    private SocketChannel socketChannel;
    private volatile boolean started;

    public ClientHandler(String ip, int port) {
        this.host = ip;
        this.port = port;
        try {
            // 创建多路复用器
            selector = Selector.open();
            // 打开监听通道
            socketChannel = SocketChannel.open();
            // 如果为 true，则此通道将被置于阻塞模式；如果为 false，则此通道将被置于非阻塞模式
            // 开启非阻塞模式
            socketChannel.configureBlocking(false);
            started = true;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void stop() {
        started = false;
    }

    @Override
    public void run() {
        // 客户端运行后，先尝试与服务端建立连接
        try {
            doConnect();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        //循环遍历selector
        while (started) {
            try {
                //无论是否有读写事件发生，selector每隔1s被唤醒一次
                selector.select(1000);
                //阻塞,只有当至少一个注册的事件发生的时候才会继续.
//				selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it = keys.iterator();
                SelectionKey key = null;
                while (it.hasNext()) {
                    key = it.next();
                    it.remove();
                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        //selector关闭后会自动释放里面管理的资源
        if (selector != null)
            try {
                selector.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    /**
     * 处理接收到的"事件"
     * @param key
     * @throws IOException
     */
    @SneakyThrows
    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            SocketChannel sc = (SocketChannel) key.channel();
            if (key.isConnectable()) {
                // channel在non-block模式下，如果此时连接操作成功了，则 finishConnect()马上返回true；如果连接操作还没成功，则会返回false，
                // 所以这里应该自旋等待true返回 todo
                if (sc.finishConnect()){
                    // finished the connect opr
                    // 确定连接成功了，则注册监听读操作
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }
                else System.exit(1);
            }
            // 读消息
            if (key.isReadable()) {
                // 创建ByteBuffer，并!!开辟!!一个1M的缓冲区
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                TimeUnit.MILLISECONDS.sleep(4000);
                // 读取请求码流，返回读取到的字节数（将数据写入buffer-即写模式）
                int readBytes = sc.read(buffer);
                // 读取到字节，对字节进行编解码
                if (readBytes > 0) {
                    // flip方法将Buffer从写模式切换到读模式（调用flip()方法会将position设回0，并将limit设置成之前position的值）
                    buffer.flip();
                    // 根据缓冲区可读字节数创建字节数组
                    byte[] bytes = new byte[buffer.remaining()];
                    //将缓冲区可读字节数组复制到新建的数组中
                    buffer.get(bytes);
                    String result = new String(bytes, StandardCharsets.UTF_8);
                    System.out.println("客户端收到消息：" + result);
                }
                //没有读取到字节 忽略
//				else if(readBytes==0);
                //链路已经关闭，释放资源
                else if (readBytes < 0) {
                    key.cancel();
                    sc.close();
                }
            }
        }
    }

    /**
     * 尝试与服务端建立连接
     * @throws IOException
     */
    private void doConnect() throws IOException {
        // 如果channel处于非阻塞模式，该方法会启动一个"建立非阻塞连接"的操作。如果连接马上成功的话（在本地连接的场景下是可能发生的），则返回true。
        // 否则返回false，并且连接操作必须通过调用 finishConnect() 确保连接操作完成
        if (socketChannel.connect(new InetSocketAddress(host, port))){
            //connect success.. do nothing
        }
        else{
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }
    }

    /**
     * 向服务端发送消息
     * @param msg
     * @throws Exception
     */
    public void sendMsg(String msg) throws Exception {
        socketChannel.register(selector, SelectionKey.OP_READ);
        doWrite(socketChannel, msg);
    }

    /**
     * 异步发送消息
     */
    private void doWrite(SocketChannel channel, String request) throws IOException {
        //将消息编码为字节数组
        byte[] bytes = request.getBytes();
        //根据数组容量创建ByteBuffer
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        // 将字节数组写到缓冲区（写模式）
        writeBuffer.put(bytes);
        // 将Buffer从写模式切换到读模式
        writeBuffer.flip();
        // 发送缓冲区的字节数组（读模式）
        channel.write(writeBuffer);
        //****此处不含处理“写半包”的代码
    }
}
