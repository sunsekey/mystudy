package com.sunsekey.practise.designpattern.creational.simplefactory;

/**
 * 具体产品类
 */
public class MountainBike extends Bike{

    @Override
    public void ride() {
        System.out.println("mountain bike rode");
    }

    @Override
    public String toString() {
        return "I am a mountain Bike!";
    }
}
