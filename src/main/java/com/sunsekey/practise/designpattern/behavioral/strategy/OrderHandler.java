package com.sunsekey.practise.designpattern.behavioral.strategy;

/**
 * 环境角色-持有策略者，决定策略怎样被使用
 */
public class OrderHandler {

    PayStrategy payStrategy;

    public OrderHandler(PayType payType){
        switch(payType){
            case ALI_PAY:
                payStrategy = new AliPayStrategy();
                break;
            case WECHAT_PAY:
                payStrategy = new WechatPayStrategy();
                break;
            default:
                break;
        }
    }

    /**
     * 支付订单
     */
    public void orderPay() {
        //other things to do
        if (payStrategy == null) {
            throw new RuntimeException("payment unsupported!");
        }
        payStrategy.checkPayStatus();
        payStrategy.pay();
        payStrategy.callback();
        //other things to do
    }

}
