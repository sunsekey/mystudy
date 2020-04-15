package com.sunsekey.practise.designpattern.structural.facade;

public class NationalDebt1 extends NationalDebt {
    @Override
    public void buy() {
        System.out.println("buy nationalDebt1");
    }

    @Override
    public void sell() {
        System.out.println("sell nationalDebt1");
    }
}
