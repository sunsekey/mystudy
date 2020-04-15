package com.sunsekey.practise.designpattern.behavioral.observer;

/**
 * 库存观察者，订单支付成功会通过库存观察者
 */
public class InventoryObserver<T> extends MyObserver<T>{

    @Override
    public void update(Object o) {
        if (o instanceof OrderPaidMySubject.OrderPaidEventData) {
            OrderPaidMySubject.OrderPaidEventData orderPaidEventData = (OrderPaidMySubject.OrderPaidEventData) o;
            Integer orderId = orderPaidEventData.getOrderId();
            Integer goodId = orderPaidEventData.getGoodId();
            System.out.println("orderId is " + orderId + " and goodId is " + goodId + " ready to update inventory");
        }
    }
}
