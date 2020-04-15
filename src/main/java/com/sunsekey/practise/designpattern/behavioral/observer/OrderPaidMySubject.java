package com.sunsekey.practise.designpattern.behavioral.observer;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 具体主题-订单支付完成 即一个event
 */
public class OrderPaidMySubject extends MySubject<OrderPaidMySubject.OrderPaidEventData> {

    List<MyObserver<OrderPaidEventData>> myObservers = new ArrayList<>();

    OrderPaidEventData eventData;

    public OrderPaidMySubject(OrderPaidEventData eventData) {
        this.eventData = eventData;
    }

    @Override
    public void attach(MyObserver<OrderPaidEventData> myObserver) {
        myObservers.add(myObserver);
    }

    @Override
    public void detach(MyObserver<OrderPaidEventData> myObserver) {
        myObservers.add(myObserver);
    }

    @Override
    public void notifyAllObserver() {
        for (MyObserver<OrderPaidEventData> myObserver : myObservers) {
            myObserver.update(eventData);
        }
    }

    @Data
    public static class OrderPaidEventData{
        public OrderPaidEventData(Integer orderId, Integer goodId) {
            this.orderId = orderId;
            this.goodId = goodId;
        }
        private Integer orderId;
        private Integer goodId;
    }
}
