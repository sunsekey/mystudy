package com.sunsekey.practise.designpattern.creational.simplefactory;

/**
 * 具体产品类
 */
public class RoadBike extends Bike {
    @Override
    public void ride() {
        System.out.println("road bike rode");
    }

    @Override
    public String toString() {
        return "I am a road Bike!";
    }
}
