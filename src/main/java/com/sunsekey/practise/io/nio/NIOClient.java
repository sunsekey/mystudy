package com.sunsekey.practise.io.nio;

/**
 * 客户端
 */
public class NIOClient {
    private static String DEFAULT_HOST = "127.0.0.1";
    private static int DEFAULT_PORT = 8888;
    private static ClientHandler clientHandler;

    /**
     * 启动客户端
     */
    public static void start(){
        start(DEFAULT_HOST,DEFAULT_PORT);
    }
    public static synchronized void start(String ip,int port){
        if(clientHandler !=null)
            clientHandler.stop();
        clientHandler = new ClientHandler(ip,port);
        // NIO具体实现都委托给clientHandler处理了
        new Thread(clientHandler,"Server").start();
    }
    // 委托clientHandler向服务器发送消息
    public static boolean sendMsg(String msg) throws Exception{
        if(msg.equals("q")) return false;
        clientHandler.sendMsg(msg);
        return true;
    }
    public static void main(String[] args){
        start();
    }
}