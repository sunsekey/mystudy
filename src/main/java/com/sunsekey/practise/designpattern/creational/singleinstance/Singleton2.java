package com.sunsekey.practise.designpattern.creational.singleinstance;

/*饿汉式单例模式 **/
public class Singleton2 {

    /* 在类初始化阶段直接创建实例，之后每次获取直接返回该实例，不存在线程安全问题，缺点是不需要该对象也先创建了，可能会浪费资源**/
    private static Singleton2 singleTon = new Singleton2();
    private String remark;

    private Singleton2() {

    }

    private Singleton2(String remark) {
        this.remark = remark;
    }

    public static Singleton2 getInstance() {
        return singleTon;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
