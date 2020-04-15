package com.sunsekey.practise.designpattern.creational.factorymethod;

/**
 * 抽象支付处理器工厂
 */
public abstract class AbstractPaymentProcessorFactory {

    // 通过子类确定具体要生产哪一种支付处理器
    public abstract PaymentProcessor manufacture();

    public static AbstractPaymentProcessorFactory getConcretePaymentProcessorFactory(PaymentProcessorType paymentProcessorType) {
        switch (paymentProcessorType){
            case ALI_PAY:
                return AliPaymentProcessorFactory.getInstance();
            case WECHAT_PAY:
                return WechatPaymentProcessorFactory.getInstance();
            default:
                throw new RuntimeException("unsupported factory");
        }
    }

}
