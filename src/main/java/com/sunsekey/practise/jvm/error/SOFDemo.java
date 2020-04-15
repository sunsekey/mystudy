package com.sunsekey.practise.jvm.error;

/**
 * StackOverflowError，栈溢出。申请的栈深度超过限定深度
 */
public class SOFDemo {

    static Integer count = 0;

    public static void main(String[] args) {
        SOFDemo sofDemo = new SOFDemo();
        try {
            sofDemo.sofTest();
        } catch (Error e) {
            e.printStackTrace();
        }
        System.out.println(count);
    }

    public void sofTest() {
        count++;
        sofTest();
    }
}
