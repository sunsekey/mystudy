package com.sunsekey.practise.designpattern.behavioral.observer;

/**
 * 主题-被观察者、Event
 */
public abstract class MySubject<T> {

    public abstract void attach(MyObserver<T> myObserver);

    public abstract void detach(MyObserver<T> myObserver);

    public abstract void notifyAllObserver();
}
