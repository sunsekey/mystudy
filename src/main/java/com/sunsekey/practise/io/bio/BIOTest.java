package com.sunsekey.practise.io.bio;

import java.io.IOException;
import java.util.Random;

/**
 * 传统BIO
 * 服务端开启一条线程，然后轮询等待客户端的连接
 * 当有连接过来，服务端获得一个socket，并创建一条新线程去处理这个socket链路 1:1
 * 缺点：
 * 1）每有一个客户端到来，服务端都需要建立新的线程去处理，当并发高时，就会耗费大量资源
 * 2）上下文切换开销大
 *
 *
 *
 * 改进：使用线程池，复用线程 n:m（n远少于m）
 * 仍有缺点：因为IO操作是阻塞的，大量并发+传输数据量大时，很多线程会受到阻塞
 *
 * 参考 https://blog.csdn.net/anxpp/article/details/51512200
 */
public class BIOTest {

    //测试主方法
    public static void main(String[] args) throws InterruptedException {
        // 运行服务器
        new Thread(() -> {
            try {
                BIOServer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        //避免客户端先于服务器启动前执行代码
        Thread.sleep(100);

        char[] operators = {'+','-','*','/'};
        Random random = new Random(System.currentTimeMillis());

        // 运行客户端
        new Thread(() -> {
            while(true){
                //随机产生算术表达式
                String expression = random.nextInt(10)+""+operators[random.nextInt(4)]+(random.nextInt(10)+1);
                BIOClient.send(expression);
                try {
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
