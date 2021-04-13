package com.sunsekey.algorithm.fibonacci;

public class RectangleCover {
    public static void main(String[] args) {
        System.out.println(findCoverWay(1));
        System.out.println(findCoverWay(2));
        System.out.println(findCoverWay(3));
        System.out.println(findCoverWay(4));
        System.out.println(findCoverWay(5));
        System.out.println(findCoverWay(6));
        System.out.println(findCoverWay(7));
        System.out.println(findCoverWay(8));
        System.out.println(findCoverWay(9));
        System.out.println(findCoverWay(10));
    }

    private static int findCoverWay(int n){
        int number = 1;
        int sum = 1;
        if (n <= 0) return 0;
        if (n == 1) {
            return 1;
        }
        // 和从传统斐波那契数列中找第n个不同，这里需要>=2
        while (n-- >= 2) {
            sum += number;
            number = sum - number;
        }
        return sum;
    }
}

