package com.sunsekey.algorithm.struct;

import com.sunsekey.algorithm.common.OneWayLink;

/**
 *
 */
public class ReverseLink {

    public static void main(String[] args) {
        OneWayLink<Integer> head = new OneWayLink<>(10
                , new OneWayLink<>(5
                , new OneWayLink<>(7
                , new OneWayLink<>(14
                , new OneWayLink<>(11, null)))));
        OneWayLink.printLink(head);
        OneWayLink.printLink(reverseLink(head));
    }

    /**
     * 利用一个tmp指针，一个一个节点去反转指向
     * @param head
     * @return
     */
    public static OneWayLink<Integer> reverseLink(OneWayLink<Integer> head) {
        OneWayLink<Integer> pre = null;
        OneWayLink<Integer> curr = head;
        while (curr != null) {
            OneWayLink<Integer> tmp = curr.getNext();
            curr.setNext(pre);
            pre = curr;
            curr = tmp;
        }
        return pre;
    }
}
