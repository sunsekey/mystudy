package com.sunsekey.practise.designpattern.behavioral.template;

/**
 * 模版方法：将固定的代码抽象到父类，部分代码交由子类去具体实现。
 * 优点：提高代码复用
 * 缺点：增加了类的数量
 *
 */
public class TemplateDemo {

    public static void main(String[] args) {
        PayTemplate payTemplate = new AliPayProcessor();
        payTemplate.execute();
    }
}
