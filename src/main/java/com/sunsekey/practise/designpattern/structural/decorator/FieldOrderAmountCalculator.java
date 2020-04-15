package com.sunsekey.practise.designpattern.structural.decorator;

/**
 * 场地订单金额计算器
 */
public class FieldOrderAmountCalculator extends AmountCalculator {
    @Override
    public void calculateAmount() {
        calculatePayAmount();
    }

    public void calculatePayAmount() {
        System.out.println("calculate field order's pay amount");
    }
}
