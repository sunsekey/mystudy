package com.sunsekey.practise.designpattern.creational.abstractfactory;

import java.math.BigDecimal;

/**
 * 一个计算器产品，用于计算不同会员的支付金额和折扣金额
 */
public abstract class Calculator {

    public abstract BigDecimal calculateAmount();

}
