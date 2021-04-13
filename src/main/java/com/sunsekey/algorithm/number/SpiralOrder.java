package com.sunsekey.algorithm.number;

import java.util.ArrayList;
import java.util.List;

/**
 * 输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字
 */
public class SpiralOrder {

    /**
     * 重点是while循环的条件 终止行号大于起始行号，终止列号大于起始列号
     * 因为是不停地顺时针走，所以每个循环都有四个步骤是肯定会做的：
     * 从左往右，从上到下，从右到左，从下到上（后两者需判断会不会重复了）
     * 每次循环结束的时候再"收缩一圈"，直到不满足"终止行号大于起始行号，终止列号大于起始列号"
     * @param matrix
     * @return
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        if (matrix == null || matrix.length == 0) {
            return res;
        }
        int r1 = 0, r2 = matrix.length - 1;
        int c1 = 0, c2 = matrix[0].length - 1;
        while (r1 <= r2 && c1 <= c2) { // 从左往右
            for (int c = c1; c <= c2; c++) {
                res.add(matrix[r1][c]);
            }// 从上往下
            for (int r = r1 + 1; r <= r2; r++) {
                res.add(matrix[r][c2]);
            }// 判断是否会重复打印
            if (r1 < r2 && c1 < c2) { // 从右往左
                for (int c = c2 - 1; c > c1; c--) {
                    res.add(matrix[r2][c]);
                }// 从下往上
                for (int r = r2; r > r1; r--) {
                    res.add(matrix[r][c1]);
                }
            }
            r1++;
            r2--;
            c1++;
            c2--;
        }
        return res;
    }
}
