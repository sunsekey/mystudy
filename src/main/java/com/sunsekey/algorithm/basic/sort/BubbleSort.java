package com.sunsekey.algorithm.basic.sort;

// https://www.cnblogs.com/xiaoming0601/p/5866048.html
public class BubbleSort {

    public static void main(String[] args) {
        int[] arr = {38, 4, 52, 10, 7, 10, 99, 66, 2};
        sort(arr);
        for (int value : arr) {
            System.out.println(value);
        }
    }

    public static void sort(int[] arr) {
        boolean swap = false;
        int tmp;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] < arr[j]) {
                    swap = true;
                    tmp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tmp;
                }
            }
            if(!swap){
                break;
            }
            swap = false;
        }
    }

}
