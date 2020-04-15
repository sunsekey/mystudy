package com.sunsekey.practise.designpattern.creational.abstractfactory;

import java.math.BigDecimal;

/**
 * 抽象工厂模式：在抽象产品基础上再作了一次抽象，即产品族抽象类(这里即Calculator)，然后产品族抽象类下有多个产品抽象类（相当于产品线），这里即regular会员金额计算器和vip会员金额计算器两条产品线。
 * 另一边，抽象工厂定义了每个子工厂可以生产什么产品，这里即RegularMemberCalculatorFactory和VipMemberCalculatorFactory，这两个工厂分别对应了两条产品线。
 * 每个产品线下有多个具体产品，这里即RegularMemberPayAmountCalculator、VipMemberDiscountAmountCalculator等
 * 优点：客户端只需要知道工厂即可以，不需要知道具体产品的类（这个和方法工厂一样）。每个工厂能生产多种具体产品（构成了一条产品线）。
 * 缺点：如果要增加一个新的具体产品，抽象工厂接口要修改代码，增加对这个新产品的生产方法。
 * 适用场景：有不同产品线要处理，并且每个产品线需要的构件基本一样，可以考虑适用抽象工厂
 */
public class AbstractFactoryDemo {

    public static void main(String[] args) {
        AbstractCalculatorFactory regularMemberCalculatorFactory = new RegularMemberCalculatorFactory();
        BigDecimal regularMemberPayAmount = regularMemberCalculatorFactory.getPayAmountCalculator().calculateAmount();
        System.out.println("regular member pay amount: " + regularMemberPayAmount);
        BigDecimal regularMemberDiscountAmount = regularMemberCalculatorFactory.getDiscountAmountCalculator().calculateAmount();
        System.out.println("regular member discount amount: " + regularMemberDiscountAmount);
        System.out.println("regular member should pay: " + regularMemberPayAmount.subtract(regularMemberDiscountAmount).toPlainString());

        AbstractCalculatorFactory vipMemberCalculatorFactory = new VipMemberCalculatorFactory();
        BigDecimal vipMemberPayAmount = vipMemberCalculatorFactory.getPayAmountCalculator().calculateAmount();
        System.out.println("vip member pay amount: " + vipMemberPayAmount);
        BigDecimal vipMemberDiscountAmount = vipMemberCalculatorFactory.getDiscountAmountCalculator().calculateAmount();
        System.out.println("vip member discount amount: " + vipMemberDiscountAmount);
        System.out.println("vip member should pay: " + vipMemberPayAmount.subtract(vipMemberDiscountAmount).toPlainString());

    }
}
