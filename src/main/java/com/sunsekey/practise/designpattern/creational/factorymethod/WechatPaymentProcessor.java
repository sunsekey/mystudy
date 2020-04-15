package com.sunsekey.practise.designpattern.creational.factorymethod;

public class WechatPaymentProcessor extends PaymentProcessor {
    @Override
    public void checkPayStatus() {
        System.out.println("weChat pay status");
    }

    @Override
    public void pay() {
        System.out.println("pay by weChat");
    }

    @Override
    public void callback() {
        System.out.println("weChat callback");
    }
}
