package com.sunsekey.practise.designpattern.structural.proxy.dynamic.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class JdkProxyDemo {

    public static void main(String[] args) {
        Dog dog = new Dog("gigi");
        ClassLoader cl = dog.getClass().getClassLoader();
        Class[] clazzs =  dog.getClass().getInterfaces();
        InvocationHandler dogInvocationHandler = new MyDogInvocationHandler(dog);
        Object o = Proxy.newProxyInstance(cl, clazzs, dogInvocationHandler);
        Animal dogProxy = (Animal) o;
        dogProxy.born();
        dogProxy.die();
    }
}
