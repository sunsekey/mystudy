package com.sunsekey.practise.designpattern.creational.abstractfactory;

import java.math.BigDecimal;

public class RegularMemberDiscountAmountCalculator extends RegularMemberCalculator{
    @Override
    public BigDecimal calculateAmount() {
        return new BigDecimal("9.9");
    }
}
