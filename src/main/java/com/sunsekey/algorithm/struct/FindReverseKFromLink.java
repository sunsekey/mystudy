package com.sunsekey.algorithm.struct;

import com.sunsekey.algorithm.common.OneWayLink;

/**
 * 求链表中倒数第 K 个节点
 * 输入一个链表，输出该链表中倒数第 k 个结点。
 */
public class FindReverseKFromLink {

    public static void main(String[] args) {

    }

    /**
     * 定义两个指针，让一个指针先走K步，另一个指针再开始走，第一个指针到尾时，另一个指针就到倒数第K个元素
     * @param head
     * @param k
     * @return
     */
    public OneWayLink<Integer> findKthToTail(OneWayLink<Integer> head, int k) {
        if (head == null || k < 1) {
            return null;
        }
        OneWayLink<Integer> fast = head;
        OneWayLink<Integer> slow = head;
        while (k-- > 1) {
            if (fast.getNext() == null) {
                return null;
            }
            fast = fast.getNext();
        }
        while (fast.getNext() != null) {
            fast = fast.getNext();
            slow = slow.getNext();
        }
        return slow;
    }

    /**
     * 扩展题
     * 给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点
     *
     * 思路：
     * 还是按上面的方法找到倒数第n个节点，然后删除；而针对返回链表头节点，之所以有这个要求，是因为有可能要删除的是头节点，即n等于链表长度
     * 这种情况则删除首节点后返回首节点的下一个节点（即快指针第一次移动就直接等于null的情况）
     */
}
