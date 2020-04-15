package com.sunsekey.practise.io.nio;

public class NIOServer {

    private static int DEFAULT_PORT = 8888;
    private static ServerHandler serverHandler;

    public static void start() {
        start(DEFAULT_PORT);
    }

    public static synchronized void start(int port) {
        if (serverHandler != null)
            serverHandler.stop();
        serverHandler = new ServerHandler(port);
        new Thread(serverHandler, "Server").start();
    }

    public static void main(String[] args) {
        start();
    }
}
