package com.sunsekey.practise.designpattern.creational.abstractfactory;

import java.math.BigDecimal;

/**
 * vip会员金额计算器
 */
public abstract class VipMemberCalculator extends Calculator{

    @Override
    public abstract BigDecimal calculateAmount();

}
