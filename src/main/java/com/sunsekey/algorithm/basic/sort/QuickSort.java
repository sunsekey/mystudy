package com.sunsekey.algorithm.basic.sort;

// 78,4,52,10,7,10,99,66,2
public class QuickSort {

    public static void main(String[] args) {
        int[] arr = {38, 4, 52, 10, 7, 10, 99, 66, 2};
//        int[] arr = {78,99,66,52,10,10,7,4,2};
        sort(0, arr.length - 1, arr);
        for (int value : arr) {
            System.out.println(value);
        }
    }

    public static void sort(int left, int right, int[] arr) {
        if (left > right) {
            return;
        }
        int val = arr[left];
        int i = left;
        int j = right;
        while (i < j) {
            while (i < j && val <= arr[j]) {
                j--;
            }
            while (i < j && val >= arr[i]) {
                i++;
            }
            //i和j指向的元素交换
            if (i < j) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        // 此时已保证了arr[1~i] 的元素都比val小且arr[i~len-1]的元素都比val大
        // 那么将val和arr[i]交换，即arr[0~i-1]的元素都比arr[i]小，arr[i+1,len-1]都比arr[i]大，即arr[i]的位置已经正确了
        // 此时递归处理左右两边的元素就行了
        arr[left] = arr[i];
        arr[i] = val;
        sort(left, i - 1, arr);
        sort(i + 1, right, arr);
    }

}
