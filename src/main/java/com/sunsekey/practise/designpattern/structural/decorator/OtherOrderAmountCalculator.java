package com.sunsekey.practise.designpattern.structural.decorator;

/**
 * 其他订单金额计算器
 */
public class OtherOrderAmountCalculator extends AmountCalculator {
    @Override
    public void calculateAmount() {
        System.out.println("calculate other order's amount");
    }

}
