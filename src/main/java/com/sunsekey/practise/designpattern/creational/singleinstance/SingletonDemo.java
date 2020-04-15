package com.sunsekey.practise.designpattern.creational.singleinstance;

/**
 * 单例模式保证了类的实例只存在一个
 */
public class SingletonDemo {

    public static void main(String[] args) {
        Singleton1 singleton1_1 = Singleton1.getInstance();
        Singleton1 singleton1_2 = Singleton1.getInstance();
        assert singleton1_1.equals(singleton1_2); // true
        Singleton2 singleton2_1 = Singleton2.getInstance();
        Singleton2 singleton2_2 = Singleton2.getInstance();
        assert singleton2_1.equals(singleton2_2);// true

        Singleton3 singleton3_1 = Singleton3.getInstance();
        Singleton3 singleton3_2 = Singleton3.getInstance();
        assert singleton3_1.equals(singleton3_2);// true

        Singleton4 singleton4_1 = Singleton4.getInstance();
        Singleton4 singleton4_2 = Singleton4.getInstance();
        assert singleton4_1.equals(singleton4_2); // true

    }
}
