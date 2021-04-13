package com.sunsekey.algorithm.number;

/**
 * 在一个二维数组中，每一行都按照从左到右递增的顺序排序，每一列 都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一 个整数，判断数组中是否含有该整数。
 */
public class FindNumInTwoDimensionalArray {

    /**
     * 思路一：从右上角或左下角找起
     * 因为天然有序，第一个数从右上角开始比较，如果大于它，则这一行都可以跳过了，反之，则只需要看这一行
     */
    public static boolean findNum(int[][] arr, int target){
        if(arr == null || arr.length == 0){
            return false;
        }
        return false;
    }
    /**
     * 思路二：二分法
     * 每次先找到mid，target < mid则下次在mid前面的区域找， 反之在mid之后的区域找
     */
    public static boolean findNum2(int[][] arr, int target){
        if(arr == null || arr.length == 0){
            return false;
        }
        return false;
    }

}
