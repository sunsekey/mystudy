package com.sunsekey.practise.jvm.javastack;

/**
 * 虚拟机栈-局部变量表
 */
public class localVariableTableDemo {

    public static void main(String[] args) {
        simpleCal();
    }

    /**
     * 利用jclasslib反编译可以看到编译后的局部变量表
     * view-show byte code..
     */
    public static void simpleCal(){
        int a = 10;
        int b = 20;
        int c = a + b;
        System.out.println(c);
    }
}
