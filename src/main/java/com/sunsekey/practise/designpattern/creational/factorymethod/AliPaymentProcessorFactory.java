package com.sunsekey.practise.designpattern.creational.factorymethod;

public class AliPaymentProcessorFactory extends AbstractPaymentProcessorFactory {

    private static AliPaymentProcessorFactory aliPaymentProcessorFactory = new AliPaymentProcessorFactory();

    private AliPaymentProcessorFactory() {

    }

    @Override
    public AliPaymentProcessor manufacture() {
        return new AliPaymentProcessor();
    }

    public static AliPaymentProcessorFactory getInstance() {
        return aliPaymentProcessorFactory;
    }

}
