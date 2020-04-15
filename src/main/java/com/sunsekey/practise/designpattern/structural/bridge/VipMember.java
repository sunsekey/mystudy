package com.sunsekey.practise.designpattern.structural.bridge;

public class VipMember extends Member {

    public VipMember(BonusFactory bonusFactory,Integer id,String nickName){
        super(bonusFactory, id, nickName);
    }

    @Override
    public void register() {
        System.out.println("vip member register,ready to get bonus");
        super.register();
    }
}
