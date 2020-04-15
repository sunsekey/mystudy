package com.sunsekey.practise.javabasic._collection;

import java.util.HashSet;

/**
 * set底层维护了一个HashMap，由此实现了不可重复(利用map的key)
 */
public class HashSetDemo {

    public static void main(String[] args) {
        HashSet<String> set = new HashSet<>();
        set.add("hello");
        set.add("world");
        set.add("hello");
        System.out.println(set);
    }
}
