package com.sunsekey.practise.designpattern.structural.proxy.dynamic.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyDogInvocationHandler implements InvocationHandler {

    private Dog dog;

    public MyDogInvocationHandler(Dog dog) {
        this.dog = dog;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy start...");
        Object object = method.invoke(dog);
        System.out.println("proxy end...");
        return object;
    }
}
