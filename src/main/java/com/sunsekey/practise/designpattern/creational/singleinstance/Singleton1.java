package com.sunsekey.practise.designpattern.creational.singleinstance;

/**
 * synchronized实现单例模式
 */
public class Singleton1 {

    private static Singleton1 singleTon = null;
    private String remark;

    private Singleton1(String remark) {
        this.remark = remark;
    }

    /* 这里加上synchronized保证了每次只有一个线程进入该方法，但同步阻塞会降低效率**/
    public static synchronized Singleton1 getInstance() {
        if (singleTon == null) {
            return new Singleton1("hello world");
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
