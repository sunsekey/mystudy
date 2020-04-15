package com.sunsekey.practise.designpattern.creational.abstractfactory;

import java.math.BigDecimal;

/**
 * 普通会员计算器
 */
public abstract class RegularMemberCalculator extends Calculator{

    @Override
    public abstract BigDecimal calculateAmount();
}
