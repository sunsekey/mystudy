package com.sunsekey.algorithm.basic.sort;

// https://www.cnblogs.com/chengxiao/p/6194356.html
public class MergeSort {

    public static void main(String[] args) {
        int[] arr = {38, 4, 52, 10, 7, 10, 99, 66, 2};
        int[] tmp = new int[arr.length];
//        int[] arr = {78,99,66,52,10,10,7,4,2};
        sort(0, arr.length - 1, arr, tmp);
        for (int value : arr) {
            System.out.println(value);
        }
    }

    public static void sort(int left, int right, int[] arr, int[] tmp) {
        if (left < right) {
            int mid = (left + right) / 2;
            sort(left, mid, arr, tmp);
            sort(mid + 1, right, arr, tmp);
            merge(left, right, mid, arr, tmp);
        }
    }

    public static void merge(int left, int right, int mid, int[] arr, int[] tmp) {
        int i = left; // 左part的起始指针
        int j = mid + 1; // 右part的起始指针
        int t = 0;
        while (i <= mid && j <= right) {
            if (arr[i] < arr[j]) {
                tmp[t++] = arr[i++];
            } else {
                tmp[t++] = arr[j++];
            }
        }
        while (i <= mid) {
            tmp[t++] = arr[i++];
        }
        while (j <= right) {
            tmp[t++] = arr[j++];
        }
        t = 0;
        while (left <= right) {
            arr[left++] = tmp[t++];
        }
    }

}
