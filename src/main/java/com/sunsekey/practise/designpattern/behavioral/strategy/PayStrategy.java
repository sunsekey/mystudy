package com.sunsekey.practise.designpattern.behavioral.strategy;

public abstract class PayStrategy {

    public abstract void checkPayStatus();

    public abstract void pay();

    public abstract void callback();

}
