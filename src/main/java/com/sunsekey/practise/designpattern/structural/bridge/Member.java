package com.sunsekey.practise.designpattern.structural.bridge;

import lombok.Data;

@Data
public abstract class Member {

    protected Integer id;

    protected String nickName;

    public Member(BonusFactory bonusFactory,Integer id,String nickName) {
        this.bonusFactory = bonusFactory;
        this.id = id;
        this.nickName = nickName;
    }

    protected BonusFactory bonusFactory;

    public void register(){
        if (bonusFactory != null) {
            bonusFactory.award(this);
        }
    }
}
