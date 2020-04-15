package com.sunsekey.practise.designpattern.structural.bridge;

public class RegularMember extends Member {

    public RegularMember(BonusFactory bonusFactory,Integer id,String nickName) {
        super(bonusFactory, id, nickName);
    }

    @Override
    public void register() {
        System.out.println("regular member register,ready to get bonus");
        super.register();
    }
}
