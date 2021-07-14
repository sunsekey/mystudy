package com.sunsekey.interview;

/*
给定一个含有 n 个正整数的数组和一个正整数 target 。

找出该数组中满足其和 ≥ target 的长度最小的 连续子数组 [num1, num2, ..., numn] ，并返回其长度。如果不存在符合条件的子数组，返回 0 。

示例 1：

输入：target = 7, nums = [2,3,1,2,4,3]
输出：2
解释：子数组 [4,3] 是该条件下的长度最小的子数组。
示例 2：

输入：target = 4, nums = [1,4,4]
输出：1
示例 3：

输入：target = 11, nums = [1,1,1,1,1,1,1,1]
输出：0

class Solution {
    public int minSubArrayLen(int target, int[] nums) {

    }
}
 */
public class AliDemo {

    public static void main(String[] args) {
        int target = 11;
        int[] nums = {1,1,1,1,1,1,1,1};
        System.out.println(minSubArrayLen(target, nums));
    }

    public static int MAX = Integer.MAX_VALUE;

    // 因为目的是定位到某个连续子数组，所以考虑用两个指针
    public static int minSubArrayLen(int target, int[] nums) {
        int len = nums.length;
        if (len == 0) {
            return 0;
        }

        int start = 0;
        int end = 0;
        int sum = 0;
        int res = MAX;// 临界值

        // 1、start先不动，end去移动，直到满足条件则停下来
        // 2、start开始移动，start每移动一位则看下是否还满足。
        // 2.1、满足则继续移动start
        // 2.2、不满足了就继续移动end
        while (end < len) {
            sum += nums[end];
            while (sum >= target) {
                res = Math.min(res, end - start + 1);
                sum -= nums[start];
                start++;
            }
            end++;
        }
        if (res == MAX) {
            return 0;
        }
        else {
            return res;
        }
    }

}
