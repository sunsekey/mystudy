package com.sunsekey.practise.javabasic._collection;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

public class ArrayListDemo {

    public static void main(String[] args) {
        ArrayList<Integer> iList = new ArrayList<>();
        // ArrayList(int initialCapacity)后，虽然给数组初始化了，但size仍为0（size指元素个数）
//        iList.set(2, 99); // throw IndexOutOfBoundsException
//        System.out.println(iList.size()); // 0
        Integer i = 1;
        iList.add(i);
        iList.add(2);
        iList.add(3);
        iList.add(4);
//        iList.add(2,0);
        iList.remove(i);
        iList.remove(2);
        System.out.println(iList);
    }
}
