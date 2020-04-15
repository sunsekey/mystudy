package com.sunsekey.practise.designpattern.structural.proxy.dynamic.jdk;

public class Dog implements Animal{

    private String name;

    public Dog(String name) {
        this.name = name;
    }

    @Override
    public void born() {
        System.out.println(String.format("dog %s born", name));
    }

    @Override
    public void die() {
        System.out.println(String.format("dog %s die", name));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
