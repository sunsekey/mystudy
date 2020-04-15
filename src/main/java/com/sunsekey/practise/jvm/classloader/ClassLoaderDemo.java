package com.sunsekey.practise.jvm.classloader;

import com.sunsekey.practise.concurrent.aqs.ReentrantLockDemo;

public class ClassLoaderDemo {

    //验证类加载器与类加载器间的父子关系
    public static void main(String[] args) throws Exception{
        //获取系统/应用类加载器
        ClassLoader appClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println("系统/应用类加载器：" + appClassLoader);
        //获取系统/应用类加载器的父类加载器，得到扩展类加载器
        ClassLoader extcClassLoader = appClassLoader.getParent();
        System.out.println("扩展类加载器" + extcClassLoader);
        System.out.println("扩展类加载器的加载路径：" + System.getProperty("java.ext.dirs"));
        //获取扩展类加载器的父加载器，但因根类加载器并不是用Java实现的所以不能获取
        System.out.println("扩展类的父类加载器：" + extcClassLoader.getParent());
    }
}


