package com.sunsekey.practise.designpattern.creational.abstractfactory;

/**
 * 具体子工厂，能生产针对vip会员的支付金额计算器和折扣金额计算器
 */
public class VipMemberCalculatorFactory extends AbstractCalculatorFactory {

    @Override
    public Calculator getPayAmountCalculator() {
        return new VipMemberPayAmountCalculator();
    }

    @Override
    public Calculator getDiscountAmountCalculator() {
        return new VipMemberDiscountAmountCalculator();
    }
}
