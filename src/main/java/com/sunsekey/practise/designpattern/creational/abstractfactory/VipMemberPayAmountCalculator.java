package com.sunsekey.practise.designpattern.creational.abstractfactory;

import java.math.BigDecimal;

public class VipMemberPayAmountCalculator extends VipMemberCalculator {
    @Override
    public BigDecimal calculateAmount() {
        return new BigDecimal("99.9");
    }
}
