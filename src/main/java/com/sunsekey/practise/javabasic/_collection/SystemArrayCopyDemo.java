package com.sunsekey.practise.javabasic._collection;

public class SystemArrayCopyDemo {

    public static void main(String[] args) {
        Integer[] arrS = new Integer[10];
        Integer[] arrD = new Integer[arrS.length];
        /**
         * src:源数组；	srcPos:源数组要复制的起始位置
         * dest:目的数组；	destPos:目的数组放置的起始位置
         * length:复制的长度
         * src和dest可以是同一个(参照ArrayList的add(index,ele))
         */
        System.arraycopy(arrS, 2, arrD, 3, 4);

    }
}
