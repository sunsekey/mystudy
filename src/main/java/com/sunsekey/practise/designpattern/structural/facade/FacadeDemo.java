package com.sunsekey.practise.designpattern.structural.facade;

/**
 * 外观模式：就是提供一个统一的接口，用来访问子系统（子模块等）中的一群接口，隐藏子系统中的逻辑细节
 * 类似的有适配器模式，但适配器模式是提供一个接口去让客户端适配另外一个不兼容的接口
 */
public class FacadeDemo {
    public static void main(String[] args) {
        Fund fund = new Fund();
        fund.buy();
        fund.sell();
    }
}

