package com.sunsekey.practise.jvm.classloader.demotype;

public class Son extends Father{

    public static final int SON_INT = 1;

    public Son() {
        System.out.println("make son");
    }

    static {
        System.out.println("static son");
    }
    {
        System.out.println("non static son");
    }
}
