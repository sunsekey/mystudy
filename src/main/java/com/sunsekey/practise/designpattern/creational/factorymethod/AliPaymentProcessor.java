package com.sunsekey.practise.designpattern.creational.factorymethod;

public class AliPaymentProcessor extends PaymentProcessor {
    @Override
    public void checkPayStatus() {
        System.out.println("alipay check pay status");
    }

    @Override
    public void pay() {
        System.out.println("pay by alipay");
    }

    @Override
    public void callback() {
        System.out.println("alipay callback");
    }
}
