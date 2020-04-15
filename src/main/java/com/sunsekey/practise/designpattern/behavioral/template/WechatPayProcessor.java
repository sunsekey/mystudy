package com.sunsekey.practise.designpattern.behavioral.template;

public class WechatPayProcessor extends PayTemplate {

    @Override
    public void pay() {
        System.out.println("pay by wechat");
    }
    @Override
    public void callback() {
        System.out.println("callback from wechat");
    }
}
