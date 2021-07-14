package com.sunsekey.algorithm.struct;

import java.util.Random;
import java.util.Stack;

/**
 * 定义栈的数据结构，请在该类型中实现一个能够得到栈最小元素的 min 函数
 */
public class FindMinStack {

    public static void main(String[] args) {
        FindMinStack findMinStack = new FindMinStack();
        Random random = new Random();

        for(int i = 0; i < 10; i++ ){
            int r = random.nextInt(100);
            System.out.println(r);
            findMinStack.push(r);
        }
        System.out.println("min: " + findMinStack.min());

        for(int i = 0; i < 10; i++ ){
            findMinStack.pop();
        }
        System.out.println("");
    }

    private Stack<Integer> stack1 = new Stack<>();
    private Stack<Integer> stack2 = new Stack<>();

    /**
     * 利用两个栈，stack1放所有元素，stack2放最小值，statck1正常push，statck2第一次先push，后面有比这个小的就再push进去
     * @param node
     */
    public void push(int node) {
        stack1.push(node);
        if (stack2.isEmpty()) {
            stack2.push(node);
        } else {
            if (stack2.peek() > node) {
                stack2.push(node);
            }
        }
    }

    public void pop() {
        if (stack1.pop().equals(stack2.peek())) {
            stack2.pop();
        }
    }

    public int top() {
        return stack1.peek();
    }

    public int min() {
        return stack2.peek();
    }
}
