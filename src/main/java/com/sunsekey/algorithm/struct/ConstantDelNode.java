package com.sunsekey.algorithm.struct;

import com.sunsekey.algorithm.common.OneWayLink;

/**
 * O(1)删除单向链表节点
 */
public class ConstantDelNode {

    public static void main(String[] args) {

    }

    /**
     * 思路：将后面一个节点的值赋给被删除节点，然后删除掉后面那个节点
     * @param head
     * @param deListNode
     */
    public void deleteNode(OneWayLink<Integer> head, OneWayLink<Integer> deListNode) {
        if (deListNode == null || head == null) return;
        if (head == deListNode) {
            head = null;
        } else { // 若删除节点是末尾节点，往后移一个
            if (deListNode.getNext() == null) {
                OneWayLink<Integer> pointListNode = head;
                while (pointListNode.getNext().getNext() != null) {
                    pointListNode = pointListNode.getNext();
                }
                pointListNode.setNext(null);
            } else {
                deListNode.setValue(deListNode.getNext().getValue());
                deListNode.setNext(deListNode.getNext().getNext());
            }
        }
    }
}