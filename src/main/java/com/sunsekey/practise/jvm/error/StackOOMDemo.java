package com.sunsekey.practise.jvm.error;

/**
 * 虚拟机栈OOM
 */
public class StackOOMDemo {
    public static void main(String[] args) {
        stackOOMTest();
    }

    public static void stackOOMTest() {
        while (true) {
          new Thread(()->{
              noStop();
          }).start();
        }
    }
    public static void noStop(){
        while (true) {
            // 方法一直不结束，导致该线程的虚拟栈空间得不到释放，最终导致OOM
        }
    }
}
