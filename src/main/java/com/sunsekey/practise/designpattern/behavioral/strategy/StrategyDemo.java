package com.sunsekey.practise.designpattern.behavioral.strategy;

/**
 * 策略模式：封装策略，<策略可独立于客户端而变化>。客户端根据外部条件选择某个策略来解决某个问题
 * 优点：策略类都实现于同一个接口，使得客户端可以自由切换；避免了多重if else易扩展
 * 缺点：客户端必须知道所有的策略类，并自行决定使用哪一个策略类；环境角色类中还是要写switch/if else的代码；产生过多策略类
 * 使用场景：动态选择多种复杂行为
 * 在spring boot中，可将每个策略加入到spring容器，然后根据条件值动态获取策略bean（利用application context、注解等），虽略显麻烦，但是不用写switch去决定使用哪一个具体策略
 * 和工厂模式的区别是，工厂模式更关注返回的结果（即真实产品对象，如单纯地返回一个PayProcessor实例，怎么用它的方法客户端再去决定）
 * 策略模式更关注逻辑过程，如PayStrategy包含了checkPayStatus\pay\callback三个方法，那么OrderHandler（持有策略的环境角色）的orderPay可以随意组织这三个方法的调用
 * （个人认为，实际上不同模式在实际使用，边界比较模糊）
 *
 *
 */
public class StrategyDemo {
    public static void main(String[] args) {
        OrderHandler orderHandler = new OrderHandler(PayType.ALI_PAY);
        orderHandler.orderPay();
    }
}
