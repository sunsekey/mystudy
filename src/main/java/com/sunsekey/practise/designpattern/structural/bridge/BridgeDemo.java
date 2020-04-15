package com.sunsekey.practise.designpattern.structural.bridge;

/**
 * 桥接模式：当一个类体现了两个（多个是否可以）纬度的变化，则可以将其中一个变化抽出作为抽象类，另一个变化作为实现类，两者通过聚合的方式实现关联。
 * 优点：降低不同纬度之间的耦合度，使得各自变化的扩展、修改更加容易。
 * 缺点：增加了设计和理解上的难度，而且要识别出两个独立变化的纬度，具有一定能的局限性
 * 本质：依赖倒置原则（面向接口编程）+ 合成复用原则（多用聚合，少用继承）
 */
public class BridgeDemo {

    public static void main(String[] args) {
        BonusFactory couponFactory = new CouponFactory();
        BonusFactory integralFactory = new IntegralFactory();

        // here comes a regular member to register
        // 实际会根据其他条件决定奖励优惠券还是奖励积分
        Member regularMember = new RegularMember(couponFactory,1,"John");
        regularMember.register();

        // here comes a vip member to register
        Member vipMember = new VipMember(integralFactory,2,"Mike");
        vipMember.register();

        // 如果新增一种奖励方式，只需要多加一个类；如果多加一种会员类型，也只需要多加一个类；两个纬度互不影响
    }

}
