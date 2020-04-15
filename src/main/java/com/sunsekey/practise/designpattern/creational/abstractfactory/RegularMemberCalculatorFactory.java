package com.sunsekey.practise.designpattern.creational.abstractfactory;

/**
 * 具体子工厂，能生产针对普通会员的支付金额计算器和折扣金额计算器
 */
public class RegularMemberCalculatorFactory extends AbstractCalculatorFactory {

    @Override
    public Calculator getPayAmountCalculator() {
        return new RegularMemberPayAmountCalculator();
    }

    @Override
    public Calculator getDiscountAmountCalculator() {
        return new RegularMemberDiscountAmountCalculator();
    }
}
