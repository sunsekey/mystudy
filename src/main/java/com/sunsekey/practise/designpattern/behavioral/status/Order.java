package com.sunsekey.practise.designpattern.behavioral.status;

import lombok.Data;

/**
 * 订单-环境角色
 */
@Data
public class Order {

    // 持有不同的状态
    public static OrderStatus UN_PAID;
    public static OrderStatus PAID;
    public static OrderStatus CANCELED;
    public static OrderStatus REFUNDED;

    public Order() {
        UN_PAID = new OrderUnPaidStatus(this);
        PAID = new OrderPaidStatus(this);
        CANCELED = new OrderCanceledStatus(this);
        REFUNDED = new OrderRefundedStatus(this);
        currentStatus = UN_PAID;
    }

    private OrderStatus currentStatus;


    public void pay() {
        // 可理解为，以当前这个状态去进行支付
        currentStatus.pay();
    }

    public void cancel() {
        currentStatus.cancel();
    }

    public void refund() {
        currentStatus.refund();
    }

}
