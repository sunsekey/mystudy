package com.sunsekey.practise.jvm.error;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 堆OOM
 */
public class HeapOOMDemo {

    public static void main(String[] args) {
//        List<List<Integer>> holder = new ArrayList<>();
//        while (true) {
//            System.out.println("allocate");
//            List<Integer> list = new ArrayList<>(10000);
//            holder.add(list);
//        }
        eatUpMemory();
    }

    public static void eatUpMemory() throws Error{
        List<List<Integer>> holder = new ArrayList<>();
        AtomicInteger atomicInteger = new AtomicInteger();
        boolean hasBeenGc = false;
        while (true) {
            List<Integer> list = new ArrayList<>(50000);
            holder.add(list);
//            if (!hasBeenGc && atomicInteger.incrementAndGet() > 8000) {
//                System.out.println("try to gc");
//                System.gc();
//                hasBeenGc = true;
//            }
        }
    }
}
