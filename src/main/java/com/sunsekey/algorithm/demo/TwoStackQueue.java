package com.sunsekey.algorithm.demo;

import java.util.Stack;

/**
 * 用两个栈来实现一个队列，完成队列的 Push 和 Pop 操作。 队列中的 元素为 int 类型。
 */
public class TwoStackQueue {

    private Stack<Integer> store = new Stack<>();

    private Stack<Integer> cache = new Stack<>();

    public void printQueue(){
        Integer curValue;
        while((curValue = this.pop()) != null){
            System.out.println(curValue);
        }
    }

    public TwoStackQueue push(Integer value) {
        if(value != null){
            store.push(value);
            return this;
        }
        throw new RuntimeException("value can not be null!");
    }

    public Integer pop(){
        if(cache.isEmpty()){
            if(store.isEmpty()){
                return null;
            }
            if(store.size() == 1){
                return store.pop();
            }
            while(!store.isEmpty()){
                cache.push(store.pop());
            }
        }
        return cache.pop();
    }


}
