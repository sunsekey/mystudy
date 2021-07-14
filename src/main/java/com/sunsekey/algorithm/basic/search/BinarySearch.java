package com.sunsekey.algorithm.basic.search;

import com.sunsekey.algorithm.basic.sort.BubbleSort;

/**
 * 有序查找
 * https://www.cnblogs.com/maybe2030/p/4715035.html
 * 其他查找还有树表查找、哈希查找、顺序查找、斐波那契查找（二分的升级版，不同在于分割点的选择）等
 */
public class BinarySearch {

    public static void main(String[] args) {
        int[] arr = {100, 88, 64, 59, 44, 25, 23, 14, 10, 6};
        BubbleSort.sort(arr);
        System.out.println(search(arr, 59));
    }

    public static int search(int[] arr, int value) {
        int len = arr.length;
        int low = 0;
        int high = len - 1;
        while (low <= high) {
//            int mid = (low + high) / 2; == low + (1/2) *(high - low)
            // 1 / 2 = > (arr[low] - value) / (arr[low] - arr[high]) 目标元素的相对位置，
            int mid = low + (arr[low] - value) / (arr[low] - arr[high]) * (high - low);
            System.out.println(mid);
            if (value == arr[mid]) {
                return mid;
            }
            if (value > arr[mid]) {
                high = mid - 1;
            }
            if (value < arr[mid]) {
                low = mid + 1;
            }
        }
        return -1;
    }
}
