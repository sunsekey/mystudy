package com.sunsekey.practise.designpattern.creational.simplefactory;

public class BikeFactory {

    /**
     * 根据某些状态值、条件值等等去返回相关的具体实现类。框架中经常用到，如hystrix中，根据配置的隔离策略返回具体信号量实现类。
     * @param bikeType
     * @return
     */
    public static Bike makeBike(BikeType bikeType) {
        switch (bikeType){
            case ROAD_BIKE:
                //实际应用中，创建对象（new）之前可能做了很多其他工作。这样就达到了将创建的逻辑封装到工厂中，与使用对象的逻辑隔离开来
                return new RoadBike();
            case MOUNTAIN_BIKE:
                return new MountainBike();
            default:
                return null;
        }
    }
}
