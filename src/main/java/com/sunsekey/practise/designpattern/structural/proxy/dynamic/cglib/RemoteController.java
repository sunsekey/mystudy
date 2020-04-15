package com.sunsekey.practise.designpattern.structural.proxy.dynamic.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class RemoteController implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("ready to remote control the superman...");
        return methodProxy.invokeSuper(o, objects);
    }
}
