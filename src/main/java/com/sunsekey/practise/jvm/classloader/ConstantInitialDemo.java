package com.sunsekey.practise.jvm.classloader;

import com.sunsekey.practise.jvm.classloader.demotype.Son;

public class ConstantInitialDemo {

    public static void main(String[] args) {
        // 通过子类去调用父类中的类变量，只会导致父类的初始化，而不会导致子类初始化
//        System.out.println(Son.FATHER_INT);
        // 因为常量在编译时已经放进里调用类(ConstantInitialDemo)的常量池中，不会"惊动"常量所在类，即不会触发其初始化
//        System.out.println(Son.SON_INT);
        // 数组引用对象不会触发类的初始化
//        Father[] fathers = new Father[10];
        Son son = new Son();
    }

}
