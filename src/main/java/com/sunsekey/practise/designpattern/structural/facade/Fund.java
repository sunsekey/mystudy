package com.sunsekey.practise.designpattern.structural.facade;

import lombok.Data;

@Data
public class Fund {
    private Investment stock1;
    private Investment stock2;
    private Investment nationalDebt;

    public Fund(){
        this.stock1 = new Stock1();
        this.stock2 = new Stock2();
        this.nationalDebt = new NationalDebt1();
    }

    /**
     * 具体基金经理如何操作我们不需要知道，我们只管购买或者赎回基金即可
     */
    public void buy() {
        stock1.buy();
        stock2.buy();
        nationalDebt.buy();
    }

    public void sell() {
        stock1.sell();
        nationalDebt.sell();
    }

}
