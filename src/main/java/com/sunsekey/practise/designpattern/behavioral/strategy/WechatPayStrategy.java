package com.sunsekey.practise.designpattern.behavioral.strategy;

public class WechatPayStrategy extends PayStrategy {
    @Override
    public void checkPayStatus() {
        System.out.println("wechat pay check pay status");
    }

    @Override
    public void pay() {
        System.out.println("pay by wechatpay");
    }

    @Override
    public void callback() {
        System.out.println("wechat pay callback");
    }
}
