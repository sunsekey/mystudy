package com.sunsekey.practise.designpattern.behavioral.status;

/**
 *
 */
public class OrderPaidStatus extends OrderStatus {

    public OrderPaidStatus(Order order) {
        super(order);
    }

    @Override
    public void pay() {
        System.out.println("请勿重复支付！");
    }

    @Override
    public void cancel() {
        System.out.println("订单已成功支付，不能取消！");
    }

    @Override
    public void refund() {
        System.out.println("退款成功");
        order.setCurrentStatus(Order.REFUNDED);
    }
}
