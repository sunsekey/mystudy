package com.sunsekey.algorithm.struct;

import java.util.Arrays;

public class VerifyPostSeqBST {
    public static void main(String[] args) {
        int[] arr = {2,6,5,8,7,10};
        System.out.println(verifyPostSeqBST(arr));
    }

    /**
     * 思路很简单，最后一个元素是根节点，然后遍历找到右子树开始的位置，然后分别对左右子树进行递归处理就好了
     * @param sequence
     * @return
     */
    public static boolean verifyPostSeqBST(int[] sequence) {
        if (sequence == null || sequence.length == 0) {
            return false;
        }
        int rstart = 0;
        int rootIndex = sequence.length - 1;
        for (int i = 0; i < rootIndex; i++) {
            if (sequence[i] < sequence[rootIndex])
                rstart++;
        }
        if (rstart == 0) {
            verifyPostSeqBST(Arrays.copyOfRange(sequence, 0, rootIndex));
            return true;
        }
        for (int i = rstart; i < rootIndex; i++) {
            if (sequence[i] <= sequence[rootIndex]) {
                return false;
            }
        }
        verifyPostSeqBST(Arrays.copyOfRange(sequence, 0, rstart));
        verifyPostSeqBST(Arrays.copyOfRange(sequence, rstart, rootIndex));
        return true;
    }
}
