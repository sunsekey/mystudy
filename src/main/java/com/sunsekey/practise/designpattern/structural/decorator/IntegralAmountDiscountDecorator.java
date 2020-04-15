package com.sunsekey.practise.designpattern.structural.decorator;

/**
 * 计算积分的抵扣金额
 */
public class IntegralAmountDiscountDecorator extends AmountCalculatorDecorator {

    public IntegralAmountDiscountDecorator(AmountCalculator amountCalculator) {
        super(amountCalculator);
    }

    @Override
    public void calculateAmount() {
        super.calculateAmount();
        calculateCouponAmount();
    }

    public void calculateCouponAmount() {
        System.out.println("calculate integral discount amount");
    }
}
