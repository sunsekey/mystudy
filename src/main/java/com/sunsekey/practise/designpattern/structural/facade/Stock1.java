package com.sunsekey.practise.designpattern.structural.facade;

public class Stock1 extends Stock {

    @Override
    public void buy() {
        System.out.println("buy stock1");
    }

    @Override
    public void sell() {
        System.out.println("sell stock1");
    }
}
