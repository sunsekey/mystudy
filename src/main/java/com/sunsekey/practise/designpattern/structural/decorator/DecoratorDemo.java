package com.sunsekey.practise.designpattern.structural.decorator;

/**
 * 装饰模式（Decorator），动态地给一个对象添加一些额外的职责，就增加功能来说，装饰模式比生成子类更灵活
 * 优点：装饰类和被装饰类可以独立发展；是继承的一个替代方案；可以扩展对象的功能
 * 缺点：增加系统复杂度，多层装饰不利于排查问题
 * 适用场景：需要扩展一个类的功能的时候，动态给对象增加功能（想想还有什么模式也是为了扩展功能，如代理模式..）
 * 之所以能一层层地叠加功能，是因为装饰类继承了抽象组件类
 */
public class DecoratorDemo {

    public static void main(String[] args) {
        // generate a field order amount calculator
        System.out.println("field order amount:");
        AmountCalculator filedAmountCalculator = new FieldOrderAmountCalculator();
        //if use used coupon, then calculate the coupon discount amount
        filedAmountCalculator = new CouponDiscountAmountDecorator(filedAmountCalculator);
        //if use used point, then calculate the point discount amount
        filedAmountCalculator = new IntegralAmountDiscountDecorator(filedAmountCalculator);
        filedAmountCalculator.calculateAmount();
        System.out.println("----------------------------------------------------------------");
        System.out.println("other order amount:");
        // other order only can use coupon,so only decorate by coupon discount amount decorator
        AmountCalculator otherOrderAmountCalculator = new OtherOrderAmountCalculator();
        otherOrderAmountCalculator = new CouponDiscountAmountDecorator(otherOrderAmountCalculator);
        otherOrderAmountCalculator.calculateAmount();
    }
}
