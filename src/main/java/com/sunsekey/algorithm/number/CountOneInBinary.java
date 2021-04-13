package com.sunsekey.algorithm.number;

/**
 * 输入一个整数，输出该数二进制表示中 1 的个数。其中负数用补码表示。整数
 */
public class CountOneInBinary {

    public static void main(String[] args) {
        System.out.println(numberOf1(-10));
    }

    /**
     * n&(n-1)的结果会将 n 最右边的 1 变为 0，直到 a = 0
     * PS：int为32位
     * @param n
     * @return
     */
    public static int numberOf1(int n) {
        int count = 0;
        while (n != 0) {
            count++;
            n = (n - 1) & n;
        }
        return count;
    }
}
