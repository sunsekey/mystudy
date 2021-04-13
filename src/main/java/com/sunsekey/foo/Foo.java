package com.sunsekey.foo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Foo extends BaseFoo{

    @Override
    public void foobar() {

    }

    private static void test(){
        System.out.println("begin bug code ...");
        Map<String, Integer> map = new ConcurrentHashMap<>(16);
        System.out.println(map.size());
        String hash1 = "AaAa";
        String hash2 = "BBBB";
        //hash值,相同产生死锁
        System.out.println("hash1:"+hash1.hashCode()+",hash1:"+hash2.hashCode());
        map.computeIfAbsent(hash1, key -> {
            map.putIfAbsent(hash2,1);
            return 1;
        });
        System.out.println(map.size());
        System.out.println("bug code is exec here?");
    }


    public static void main(String[] args) {
        test();
    }


}
