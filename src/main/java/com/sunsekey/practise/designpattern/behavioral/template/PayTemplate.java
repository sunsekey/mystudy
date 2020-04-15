package com.sunsekey.practise.designpattern.behavioral.template;

/**
 * 一个简化版支付模版
 */
public abstract class PayTemplate {

    final public void execute() {
        checkPayStatus();
        pay();
        callback();
    }

    protected void checkPayStatus() {
        System.out.println("check pay status from db");
    }

    protected abstract void pay();

    protected abstract void callback();

}
