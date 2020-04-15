package com.sunsekey.practise.designpattern.behavioral.status;

public abstract class OrderStatus {

    protected Order order;

    public OrderStatus(Order order) {
        this.order = order;
    }

    public abstract void pay();
    public abstract void cancel();
    public abstract void refund();

}
