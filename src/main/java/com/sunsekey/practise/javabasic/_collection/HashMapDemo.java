package com.sunsekey.practise.javabasic._collection;

import java.util.HashMap;

public class HashMapDemo {

    public static void main(String[] args) {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, null);
        map.put(1, "hello_new");
        map.put(2, "world");
        map.get(1);
        map.get(3);
    }

}
