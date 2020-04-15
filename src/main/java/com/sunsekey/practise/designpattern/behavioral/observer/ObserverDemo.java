package com.sunsekey.practise.designpattern.behavioral.observer;

/**
 * 观察者模式：定义对象间一种一对多的依赖关系，使得每当一个对象改变状态，则所有依赖于它的对象都会得到通知并自动更新
 * （主题（被观察者）持有观察者的集合）
 * 优点：观察者与被观察者之间通过松耦合实现了交互
 * 缺点：1）观察者与被观察者之间如果有循环引用则可能导致系统崩溃
 * 适用场景：多级触发场景、关联行为等
 *
 */
public class ObserverDemo {

    public static void main(String[] args) {
        // 创建一个被观察者，即事件源
        OrderPaidMySubject.OrderPaidEventData orderPaidEventData = new OrderPaidMySubject.OrderPaidEventData(12,344);
        MySubject<OrderPaidMySubject.OrderPaidEventData> orderPaidMySubject = new OrderPaidMySubject(orderPaidEventData);
        MyObserver<OrderPaidMySubject.OrderPaidEventData> inventoryObserver = new InventoryObserver<OrderPaidMySubject.OrderPaidEventData>();
        orderPaidMySubject.attach(inventoryObserver);
        // after paid
        orderPaidMySubject.notifyAllObserver();
    }


}
