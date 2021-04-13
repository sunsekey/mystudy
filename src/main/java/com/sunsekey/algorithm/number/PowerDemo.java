package com.sunsekey.algorithm.number;

/**
 * 给定一个 double 类型的浮点数 base 和 int 类型的整数 exponent。求 base 的 exponent 次方。不得使用库函数，不需要考虑大数问题
 */
public class PowerDemo {

    public static void main(String[] args) {
        System.out.println(power(1.4, 10));
    }

    /**
     * 不能用==比较两个浮点数是否相等，因为有误差。考虑输入值的多种情况。
     * @param base
     * @param exponent
     * @return
     */
    public static double power(double base, int exponent) {
        double res = 0;
        if (equal(base, 0)) {
            return 0;
        }
        if (exponent == 0) {
            return 1.0;
        }
        if (exponent > 0) {
            res = mutiply(base, exponent);
        } else {
            res = mutiply(1 / base, -exponent);
        }
        return res;
    }

    public static double mutiply(double base, int e) {
        double sum = 1;
        for (int i = 0; i < e; i++) {
            sum = sum * base;
        }
        return sum;
    }

    public static boolean equal(double a, double b) {
        return a - b < 0.000001 && a - b > -0.000001;
    }
}
