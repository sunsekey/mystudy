package com.sunsekey.practise.jvm.classloader.demotype;

public class Father{
    public static int FATHER_INT = 10;

    public Father() {
        System.out.println("make father");
    }

    static {
        System.out.println("static father");
    }
    {
        System.out.println("non static father");
    }
}
