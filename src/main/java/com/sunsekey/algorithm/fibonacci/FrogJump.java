package com.sunsekey.algorithm.fibonacci;

/**
 * 问题一：一只青蛙一次可以跳上 1 级台阶，也可以跳上 2 级。求该青蛙跳上一 个 n 级的台阶总共有多少种跳法。
 * 问题二：一只青蛙一次可以跳上 1 级台阶，也可以跳上 2 级，也可以跳上 n 级。求该青蛙跳上一 个 n 级的台阶总共有多少种跳法。
 */
public class FrogJump {

    /**
     * 举例：前5个台阶分别的跳法数，1 2 3 5 8，其实就是斐波那契数列。
     * 譬如目标是第5个台阶，那么到第3个台阶有3种跳法，这时候从3到5可以直接跳两级。
     * 到第4个台阶有5种跳法，从4到5可以跳一级，即3+5即为到第5层台阶的跳法数
     * @return
     */
    public static int countWay(int n){
        return -1;
    }


    /**
     * 通过画图可找规律，但不知道为什么是这样 //todo
     * @param n
     * @return
     */
    public static int countWay2(int n){
        return (int)Math.pow(2, n -1);
    }


}
