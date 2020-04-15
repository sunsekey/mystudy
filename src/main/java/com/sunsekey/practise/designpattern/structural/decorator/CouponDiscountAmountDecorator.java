package com.sunsekey.practise.designpattern.structural.decorator;

/**
 * 计算优惠券的抵扣金额
 */
public class CouponDiscountAmountDecorator extends AmountCalculatorDecorator {

    public CouponDiscountAmountDecorator(AmountCalculator amountCalculator) {
        super(amountCalculator);
    }

    @Override
    public void calculateAmount() {
        super.calculateAmount();
        calculateCouponAmount();
    }

    public void calculateCouponAmount() {
        System.out.println("calculate coupon discount amount");
    }
}
