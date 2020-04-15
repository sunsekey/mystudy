package com.sunsekey.practise.designpattern.behavioral.status;

/**
 *
 */
public class OrderCanceledStatus extends OrderStatus {

    public OrderCanceledStatus(Order order) {
        super(order);
    }

    @Override
    public void pay() {
        System.out.println("订单已取消，不能支付！");
    }

    @Override
    public void cancel() {
        System.out.println("订单已取消，请勿重复操作！");
    }

    @Override
    public void refund() {
        System.out.println("订单已取消状态不能进行退款！");
    }
}
