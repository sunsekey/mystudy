package com.sunsekey.practise.designpattern.structural.proxy.dynamic.cglib;

import net.sf.cglib.proxy.Enhancer;

public class CglibProxyDemo {

    public static void main(String[] as) {
//        Superman superman = new Superman("Altman");
        RemoteController remoteController = new RemoteController();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Superman.class);// 指定代理类要继承的类
        enhancer.setCallback(remoteController);// 指定代理类方法执行时的统一调用点，即remoteController的intercept方法
        Class[] argTypes = {String.class};
        Object[] args = {"Altman"};
        Superman supermanProxy = (Superman) enhancer.create(argTypes, args);
        supermanProxy.shooting();
        supermanProxy.punch();
    }
}
