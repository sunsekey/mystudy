package com.sunsekey.practise.designpattern.structural.decorator;

public abstract class AmountCalculatorDecorator extends AmountCalculator {

    protected AmountCalculator amountCalculator;

    public AmountCalculatorDecorator(AmountCalculator amountCalculator) {
        this.amountCalculator = amountCalculator;
    }

    @Override
    public void calculateAmount(){
        amountCalculator.calculateAmount();
    }
}
