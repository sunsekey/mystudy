package com.sunsekey.algorithm.struct;

import com.sunsekey.algorithm.common.OneWayLink;

public class MergeLink {
    public static void main(String[] args) {

    }

    /**
     * 递归，时间、空间复杂度都是O(m+n)
     * @param link1
     * @param link2
     * @return
     */
    public static OneWayLink<Integer> mergeLink(OneWayLink<Integer> link1, OneWayLink<Integer> link2) {
        if (link1 == null) {
            return link2;
        }
        if (link2 == null) {
            return link2;
        }
        if (link1.getValue() < link2.getValue()) {
            link1.setNext(mergeLink(link1.getNext(), link2));
            return link1;
        } else {
            link2.setNext(mergeLink(link1, link2.getNext()));
            return link2;
        }
    }

    /**
     * 迭代
     * 以下做法不产生新的链表，用一个指针，按顺序把数字串起来，指向的还是原来节点
     * @param list1
     * @param list2
     * @return
     */
    public OneWayLink<Integer> mergeTwoLists2(OneWayLink<Integer> list1, OneWayLink<Integer> list2) {
        OneWayLink<Integer> preHead = new OneWayLink<Integer>(-1, null);
        OneWayLink<Integer> pre = preHead;
        while (list1 != null && list2 != null) {
            if (list1.getValue() < list2.getValue()) {
                pre.setNext(list1);
                list1 = list1.getNext();
            } else {
                pre.setNext(list2);
                list2 = list2.getNext();
            }
            pre = pre.getNext();
        }
        // !! 最后哪个剩下的直接整个串起来
        pre.setNext(list1 == null ? list2 : list1);
        return preHead.getNext();
    }
}
