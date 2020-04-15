package com.sunsekey.practise.javabasic._bitcal;

public class BitCalDemo {

    public static void main(String[] args) {
//        System.out.println(binaryToDecimal(decimalToBinary(18)));
//        exclusiveOr(18,2);
//        System.out.println(decimalToBinary(10));
//        System.out.println(positiveBinaryToDecimal("00000101"));
        //System.out.println(leftShift(9,3));
//        System.out.println(rightShift(-16,2));
//        System.out.println(logicRightShift(-16,2));
        int X = 107;
        int r1 = X % 16;
        int r2 = X & 15;
        System.out.println(r1);
        System.out.println(r2);

//        int h;
//        int r = (h = 10) * (h / 2);
//        System.out.println(r);

    }

    /**
     * 左移运算，左移时不管正负，低位补0（shiftCount为移动位数）
     * val不断乘以2，乘shiftCount次
     */
    public static Integer leftShift(Integer val,Integer shiftCount) {
        return val << shiftCount;
    }

    /**
     * 右移运算，如果该数为正，则高位补0，若为负数，则高位补1（shiftCount为移动位数）
     * val不断除以2，除shiftCount次
     */
    public static Integer rightShift(Integer val,Integer shiftCount) {
        return val >> shiftCount;
    }

    /**
     * 逻辑右移运算(无符号右移)，如果该数为正，则高位补0，若为负数，则高位同样补0（shiftCount为移动位数）
     * val不断除以2，除shiftCount次
     */
    public static Integer logicRightShift(Integer val,Integer shiftCount) {
        return val >>> shiftCount;
    }
    /**
     * 异或，两个2进制数从高位进行运算，相同为0，不同为1
     */
    public static Integer exclusiveOr(Integer val1,Integer val2) {
        return val1 ^ val2;
    }

    /**
     * 与运算，两个2进制数从高位进行运算，都为1则为1，否则为0
     */
    public static Integer and(Integer val1,Integer val2) {
        return val1 & val2;
    }

    /**
     * 或运算，两个2进制数从高位进行运算，只要有一个为1则为1，否则就为0
     */
    public static Integer or(Integer val1,Integer val2) {
        return val1 | val2;
    }

    /**
     * 非运算，如果位为0，结果是1，如果位为1，结果是0.
     */
    public static Integer not(Integer val1) {
        return ~val1;
    }

    /**
     * 2进制=>10进制（正整数）
     * @param binaryVal
     * @return
     */
    public static Integer positiveBinaryToDecimal(String binaryVal) {
        return Integer.parseInt(binaryVal, 2);
    }

    /**
     * 正整数-10进制=>2进制
     * @param decimalVal
     * @return
     */
    public static String positiveDecimalToBinary(Integer decimalVal) {
        // 思想：十进制数循环除以2，每次余数记录下来，直到商为0
        StringBuilder str = new StringBuilder();// thread not safe
        while (decimalVal != 0) {
            str.insert(0, decimalVal % 2);// append只能往后追加
            decimalVal = decimalVal / 2;
        }
        return str.toString();
    }

    /**
     * 2进制=>10进制（正整数）
     * @param binaryVal
     * @return
     */
    public static Integer negativeBinaryToDecimal(String binaryVal) {
        /**
         * 和负数10进制->2进制反着来：
         * 负数二进制 减一
         * 然后取反获得原码
         * 转为十进制
         * 取负则完成
         */
        throw new UnsupportedOperationException();
    }

    /**
     * 负整数-10进制=>2进制
     * @param decimalVal
     * @return
     */
    public static String negativeDecimalToBinary(Integer decimalVal) {
        /**
         * 先计算原码，即正数的二进制表示
         * 然后取反
         * 然后+1得到补码，该补码即负数的二进制表示方式
         */
        throw new UnsupportedOperationException();
    }

}
