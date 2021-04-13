package com.sunsekey.algorithm.struct;

import com.sunsekey.algorithm.common.OneWayLink;

/**
 * 输入一个链表，从尾到头打印链表每个节点的值。
 */
public class ReversePrintLink {

    public static void main(String[] args) {
        OneWayLink<String> head = new OneWayLink<>("head"
                , new OneWayLink<>("No1"
                ,new OneWayLink<>("No2"
                ,new OneWayLink<>("No3"
                ,new OneWayLink<>("No4",null)))));
//        printLink(head);
        reversePrintLink(head);
    }

    /**
     * 方法2 利用栈
     */
    public void foo2(OneWayLink<String> headNode){

    }
    /**
     * 方法1，递归
     * @param headNode
     */
    public static void reversePrintLink(OneWayLink<String> headNode){
        if(headNode == null){
            return;
        }
        OneWayLink<String> next = headNode.getNext();
        if(next != null){
            reversePrintLink(next);
        }
        System.out.println(headNode.getValue());
    }

    public static void printLink(OneWayLink<String> headNode){
        OneWayLink<String> curNode = headNode;
        while(curNode.getNext() != null){
            System.out.println(curNode.getValue());
            curNode = curNode.getNext();
        }
    }

}
