package com.sunsekey.practise.designpattern.creational.factorymethod;

public class WechatPaymentProcessorFactory extends AbstractPaymentProcessorFactory {

    private static WechatPaymentProcessorFactory wechatPaymentProcessorFactory = new WechatPaymentProcessorFactory();
    @Override
    public WechatPaymentProcessor manufacture() {
        return new WechatPaymentProcessor();
    }

    public static WechatPaymentProcessorFactory getInstance() {
        return wechatPaymentProcessorFactory;
    }
}
