package com.sunsekey.algorithm.number;

/**
 * 输入数字 n，按顺序打印从 1 到最大的 n 位数十进制数，比如：输入 3，打印出 1 到 999.
 *
 * 第一思路是先求出最大值，然后循环打印。但这种思路，没考虑溢出问题，如果n很大，那么没有数据类型可以存这么大的数，long也不行
 * 这时候，可以考虑用数组，譬如n位数，就用长度位n的数组（一个数组最大的长度是一个 int 的最大值）
 *
 * 基于这个思路，我们就从低位数一个一个去打印，如从个位数1-9，完了再到十位数变成1（同时个位数归零再次遍历），可以用递归实现
 *
 * TODO
 */
public class printToNDigits {

    public static void main(String[] args) {
        printToMaxOfNDigits(3);
    }

    public static void printToMaxOfNDigits(int n) {
        int[] array = new int[n];
        if (n <= 0) return;
        printArray(array, 0);
    }

    private static void printArray(int[] array, int n) {
        for (int i = 0; i < 10; i++) {
            if (n != array.length) {
                array[n] = i;
                printArray(array, n + 1);
            } else {
                boolean isFirstNo0 = false;
                for (int j = 0; j < array.length; j++) {
                    if (array[j] != 0) {
                        System.out.print(array[j]);
                        if (!isFirstNo0) isFirstNo0 = true;
                    } else {
                        if (isFirstNo0) System.out.print(array[j]);
                    }
                }
                System.out.println();
                return;
            }
        }
    }
}
