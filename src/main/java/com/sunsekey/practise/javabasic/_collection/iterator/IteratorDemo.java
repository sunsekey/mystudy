package com.sunsekey.practise.javabasic._collection.iterator;

import java.util.ArrayList;
import java.util.Iterator;

public class IteratorDemo {

    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            arrayList.add(i);
        }
//        for (Integer integer : arrayList) {
//            System.out.println(integer);
//        }
        // 等价于
        for (Iterator<Integer> iterator = arrayList.iterator(); iterator.hasNext();) {
            Integer item = iterator.next();
            System.out.println(item);
            iterator.remove();
//            arrayList.remove(item);
        }
    }
}
