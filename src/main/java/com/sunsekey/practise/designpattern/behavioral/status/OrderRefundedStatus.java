package com.sunsekey.practise.designpattern.behavioral.status;

/**
 *
 */
public class OrderRefundedStatus extends OrderStatus {

    public OrderRefundedStatus(Order order) {
        super(order);
    }

    @Override
    public void pay() {
        System.out.println("订单已退款，不能再次支付！");
    }

    @Override
    public void cancel() {
        System.out.println("订单已退款，不能进行取消！");
    }

    @Override
    public void refund() {
        System.out.println("订单已退款，请勿重复操作！");
    }
}
