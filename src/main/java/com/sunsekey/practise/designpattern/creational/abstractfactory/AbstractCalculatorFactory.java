package com.sunsekey.practise.designpattern.creational.abstractfactory;

/**
 * 抽象工厂，定义了每个子工厂都能生产普通会员金额计算器和vip会员金额计算器
 */
public abstract class AbstractCalculatorFactory {

    /*支付金额计算器 **/
    public abstract Calculator getPayAmountCalculator();

    /*折扣金额计算器 **/
    public abstract Calculator getDiscountAmountCalculator();
}
