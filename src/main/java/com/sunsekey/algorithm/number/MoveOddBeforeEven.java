package com.sunsekey.algorithm.number;

/**
 * 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得 所有的奇数位于数组的前半部分，所有的偶数位于位于数组的后半部分，并保证奇 数和奇数，偶数和偶数之间的相对位置不变
 *
 * 思路：使用双指针法，或移动偶数位置
 */
public class MoveOddBeforeEven {

    public static void main(String[] args) {
        int[] arr = {7,11,5,6,14,9,12,3,8};
        for (int i : arr) {
            System.out.print(i);
        }
        System.out.println();
        reOrderArray(arr);
        for (int i : arr) {
            System.out.print(i);
        }
    }

    /**
     * 这种做法改变了偶数之间原有的相对位置，但思路是没问题，用双指针。可以让奇数往前面插，偶数不动。
     * @param array
     */
    public static void reOrderArray(int[] array) {
        if (array == null || array.length == 0) {
            return;
        }
        int left = 0;
        int right = array.length - 1;
        while (left < right) {
            while (left < right && array[left] % 2 != 0) {
                left++;
            }
            while (left < right && array[right] % 2 == 0) {
                right--;
            }
            if (left < right) {
                int tmp = array[left];
                array[left] = array[right];
                array[right] = tmp;
            }
        }
    }

}
