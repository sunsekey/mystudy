package com.sunsekey.practise.designpattern.behavioral.strategy;

public class AliPayStrategy extends PayStrategy {
    @Override
    public void checkPayStatus() {
        System.out.println("ali pay check pay status");
    }

    @Override
    public void pay() {
        System.out.println("pay by alipay");
    }

    @Override
    public void callback() {
        System.out.println("ali pay callback");
    }
}
