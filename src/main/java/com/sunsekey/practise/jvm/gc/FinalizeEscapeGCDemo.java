package com.sunsekey.practise.jvm.gc;
/**
 * 此代码演示了两点
 * 对象可以在GC时自我拯救
 * 这种自救只会有一次，因为一个对象的finalize方法只会被自动调用一次
 * 参考：https://blog.csdn.net/lwang_IT/article/details/78650168
 * */
public class FinalizeEscapeGCDemo {
    public static FinalizeEscapeGCDemo SAVE_HOOK = null;

    public void isAlive() {
        System.out.println("yes我还活着");
    }

    public void finalize() throws Throwable {
        super.finalize();
        System.out.println("执行finalize方法");
        FinalizeEscapeGCDemo.SAVE_HOOK = this;//自救
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK = new FinalizeEscapeGCDemo();
        //对象的第一次回收
        SAVE_HOOK = null;
        System.gc();
        //因为finalize方法的优先级很低所以暂停0.5秒等它
//        Thread.sleep(500);
        Thread.yield();
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("no我死了");
        }
        //下面的代码和上面的一样，但是这次自救却失败了
        //对象的第一次回收
        SAVE_HOOK = null;
        System.gc();
//        Thread.sleep(500);
        Thread.yield();
        if (SAVE_HOOK != null) {
            SAVE_HOOK.isAlive();
        } else {
            System.out.println("no我死了");
        }
    }
}

