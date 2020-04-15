package com.sunsekey.practise.designpattern.creational.singleinstance;

/*静态内部类单例模式 **/
public class Singleton4 {

    public static class Holder{
        private static Singleton4 singleTon = new Singleton4();
    }

    public static Singleton4 getInstance() {
        return Holder.singleTon;//只有第一次执行Holder.singleTon，singleTon=new Singleton4();才会被执行
    }

}
