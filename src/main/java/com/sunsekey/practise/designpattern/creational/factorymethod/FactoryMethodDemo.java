package com.sunsekey.practise.designpattern.creational.factorymethod;

/**
 * 工厂方法模式
 * 优点：比简单工厂更符合开闭原则，且符合单一职责原则。不同的情况对应一个工厂类去处理。
 * 缺点：产生很多类，而且一个工厂只能生产一种产品
 */
public class FactoryMethodDemo {

    public static void main(String[] args) {
        // 客户端不需要知道具体产品的类名，只需要知道工厂就行
        PaymentProcessor aliPaymentProcessor = AliPaymentProcessorFactory.getInstance().manufacture();
        PaymentProcessor wechatPaymentProcessor = WechatPaymentProcessorFactory.getInstance().manufacture();
        aliPaymentProcessor.checkPayStatus();
        aliPaymentProcessor.pay();
        aliPaymentProcessor.callback();
        wechatPaymentProcessor.checkPayStatus();
        wechatPaymentProcessor.pay();
        wechatPaymentProcessor.callback();
        System.out.println("--------after refactoring--------");
        // 重构前，客户端主动生成不同支付处理器工厂并各自生产支付处理器，各自支付
        // 重构后，客户端可根据传入支付类型，决定最终支付结果，实际应用可减少if else判断
        PaymentProcessor aliPaymentProcessor2 = PaymentProcessor.getPaymentProcessor(PaymentProcessorType.ALI_PAY);
        PaymentProcessor wechatPaymentProcessor2 = PaymentProcessor.getPaymentProcessor(PaymentProcessorType.WECHAT_PAY);
        aliPaymentProcessor2.checkPayStatus();
        aliPaymentProcessor2.pay();
        aliPaymentProcessor2.callback();
        wechatPaymentProcessor2.checkPayStatus();
        wechatPaymentProcessor2.pay();
        wechatPaymentProcessor2.callback();
    }

}
