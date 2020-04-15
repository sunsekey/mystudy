package com.sunsekey.practise.designpattern.structural.bridge;

public class IntegralFactory extends BonusFactory {
    @Override
    public void award(Member member) {
        System.out.println("reward member " + member.getNickName() + " some integrals");
    }
}
