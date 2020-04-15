package com.sunsekey.practise.designpattern.behavioral.template;

public class AliPayProcessor extends PayTemplate {

    @Override
    public void pay() {
        System.out.println("pay by alipay");
    }
    @Override
    public void callback() {
        System.out.println("callback from alipay");
    }
}
