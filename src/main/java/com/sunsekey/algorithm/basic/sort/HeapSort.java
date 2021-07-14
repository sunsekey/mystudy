package com.sunsekey.algorithm.basic.sort;

//                38
//        4               52
//
//    10      7       10      99
//
//66      2

//                38
//        2               10
//
//    4      7       52      99
//
//66      10

// https://blog.csdn.net/qq_16403141/article/details/80526313
// https://www.cnblogs.com/chengxiao/p/6129630.html
public class HeapSort {

    public static void main(String[] args) {
        int[] arr = {38, 4, 52, 10, 7, 10, 99, 66, 2};
        myHeapSort(arr);
        for (int i : arr)
            System.out.print(i + " ");
    }

    public static void myHeapSort(int[] arr) {
        int i;
        int len = arr.length;
        // 1、构建一个最小堆，i即要调整的树的根结点，从最后一个非叶子节点开始，往前遍历
        for (i = len / 2 - 1; i >= 0; i--) {
            adjustment(arr, i, len);
        }
        // 2、每次将顶点与最后一个节点交换，然后重新调整顶点即可
        for (i = len - 1; i >= 0; i--) {
            int tmp = arr[0];
            arr[0] = arr[i];
            arr[i] = tmp;
            adjustment(arr, 0, i); // 这里的调整只需要针对根结点调整，因为根结点下的结构已经满足最小堆结构
        }
    }

    public static void adjustment(int[] arr, int pos, int len) {
        int child = 2 * pos + 1;
        // 若pos的右节点存在，则child指向左右节点值较小的那个节点
        if (child + 1 < len && arr[child] > arr[child + 1]) {
            child++;
        }
        // 将pos节点的值取其与子节点中的最小值，然后继续调整
        if (child < len && arr[pos] > arr[child]) {
            int tmp = arr[pos];
            arr[pos] = arr[child];
            arr[child] = tmp;
            adjustment(arr, child, len); // 上面的交换可能会打乱之前子数的结构，重新对子树进行调整
        }
    }

}
