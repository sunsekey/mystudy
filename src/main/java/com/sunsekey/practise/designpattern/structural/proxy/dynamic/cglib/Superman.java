package com.sunsekey.practise.designpattern.structural.proxy.dynamic.cglib;

public class Superman {

    private String name;

    public Superman() {

    }

    public Superman(String name) {
        this.name = name;
    }

    public void shooting() {
        System.out.println(String.format("%s is shooting...", name));
    }

    public void punch() {
        System.out.println(String.format("%s is punching...", name));
    }

}
