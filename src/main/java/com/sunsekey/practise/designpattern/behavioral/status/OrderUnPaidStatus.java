package com.sunsekey.practise.designpattern.behavioral.status;

/**
 *
 */
public class OrderUnPaidStatus extends OrderStatus {

    public OrderUnPaidStatus(Order order) {
        super(order);
    }

    @Override
    public void pay() {
        System.out.println("支付成功");
        order.setCurrentStatus(Order.PAID);
    }

    @Override
    public void cancel() {
        System.out.println("订单已成功取消！");
        order.setCurrentStatus(Order.CANCELED);
    }

    @Override
    public void refund() {
        System.out.println("订单待支付状态不能进行退款！");
    }
}
