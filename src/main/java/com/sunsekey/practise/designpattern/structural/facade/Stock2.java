package com.sunsekey.practise.designpattern.structural.facade;

public class Stock2 extends Stock {
    @Override
    public void buy() {
        System.out.println("buy stock2");
    }

    @Override
    public void sell() {
        System.out.println("sell stock2");
    }
}
