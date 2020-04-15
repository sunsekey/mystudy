package com.sunsekey.practise.designpattern.creational.singleinstance;

/**
 * DCL(double check lock) + volatile实现单例模式
 */
public class Singleton3 {

    /* volatile在这里是为了禁止指令重排。**/
    /**
     * instance = new Singleton3();可以分为以下3步完成（伪代码
     * 1）memory = allocate();    // 分配内存对象空间
     * 2）instance = (memory);   // 初始化对象
     * 3）instance = memory;     // 设置instance指向刚分配的内存地址，此时instance != null
     *     jvm可能会对2、3指令进行重排，变成：（因为它们之间没有依赖）
     * 1）memory = allocate();    // 分配内存对象空间
     * 3）instance = memory;     // 设置instance指向刚分配的内存地址，此时instance != null
     * 2）instance = (memory);   // 初始化对象
     * 这样程序可能就会拿到没有被初始化的对象。加上volatile声明就可以避免指令重排
    */


    private static volatile Singleton3 singleTon = null;
    private String remark;

    private Singleton3() {

    }

    private Singleton3(String remark) {
        this.remark = remark;
    }

    public static Singleton3 getInstance() {
        if (singleTon == null) {
            synchronized (Singleton3.class){
                if (singleTon == null) {
                    return new Singleton3();
                }
                return singleTon;
            }
        }
        return singleTon;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
