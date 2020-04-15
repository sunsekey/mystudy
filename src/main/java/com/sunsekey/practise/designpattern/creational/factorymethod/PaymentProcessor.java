package com.sunsekey.practise.designpattern.creational.factorymethod;

/**
 * 抽象产品类
 */
public abstract class PaymentProcessor {

    public abstract void checkPayStatus();

    public abstract void pay();

    public abstract void callback();

    public static PaymentProcessor getPaymentProcessor(PaymentProcessorType paymentProcessorType) {
        return AbstractPaymentProcessorFactory.getConcretePaymentProcessorFactory(paymentProcessorType).manufacture();
    }

}
