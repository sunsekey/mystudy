package com.sunsekey.practise.designpattern.creational.abstractfactory;

import java.math.BigDecimal;

/**
 *
 */
public class VipMemberDiscountAmountCalculator extends VipMemberCalculator {
    @Override
    public BigDecimal calculateAmount() {
        return new BigDecimal("19.9");
    }
}
