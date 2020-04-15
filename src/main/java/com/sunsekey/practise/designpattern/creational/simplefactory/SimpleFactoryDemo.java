package com.sunsekey.practise.designpattern.creational.simplefactory;

/**
 * 简单工厂方法
 * 应用场景：1、具体产品类不多且比较固定，每种产品根据某种条件而去创建（如果经常需要新增产品，工厂类就需要经常被改动，
 * 不符合开闭原则(对扩展是开放的,而对修改是封闭的)）
 * 2、使用者不需要知道创建的逻辑时
 */
public class SimpleFactoryDemo {

    public static void main(String[] args) {
        Bike mountainBike = BikeFactory.makeBike(BikeType.MOUNTAIN_BIKE);
        Bike roadBike = BikeFactory.makeBike(BikeType.ROAD_BIKE);
        System.out.println(mountainBike);
        System.out.println(roadBike);
    }
}
