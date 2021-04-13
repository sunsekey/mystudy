package com.sunsekey.algorithm.number;

/**
 * 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的 旋转。
 * 输入一个<p>非递减排序</p>的数组的一个旋转，输出旋转数组的最小元素。
 * 例如 数组{3,4,5,1,2}为{1,2,3,4,5}的一个旋转，该数组的最小值为 1。
 * NOTE：给出的所 有元素都大于 0，若数组大小为 0，请返回-1。假设数组中不存在重复元素。
 */
public class FindMinInAscendingSpinArray {

    /**
     * KEY POINT ONE:
     * 关键在于搜索转折点，那么判断停止搜索的条件就是 arr[index] > arr[index + 1]，arr[index + 1] 为最小值
     * 或arr[index - 1] > arr[index]，arr[index] 为最小值
     * KEY POINT TWO:
     * 非递减的排序则为递增排序，基于这一点，利用二分法，比较中间元素与第一个元素大小，中间元素大，则只需在右半部分继续找转折点
     * 反之，则在左半部分找
     *
     * @param arr
     * @return
     */
    public static int find(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        if (arr.length == 1 || arr[arr.length - 1] > arr[0]) {
            return arr[0];
        }
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (arr[mid] > arr[mid + 1]) {
                return arr[mid + 1];
            }
            if (arr[mid - 1] > arr[mid]) {
                return arr[mid];
            }
            if (arr[mid] > arr[left]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    public static int findWhileEleRepeat(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        if (arr.length == 1 || arr[arr.length - 1] > arr[0]) {
            return arr[0];
        }
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            /* 处理元素重复的情况 **/
            // 将中间元素与左边元素作判断，如果相等，则左游标往前走
            while (arr[mid] == arr[left] && mid > left) {
                left++;
            }
            if (arr[left] < arr[right])
                return arr[left];
            if (arr[mid] > arr[mid + 1]) {
                return arr[mid + 1];
            }
            if (arr[mid - 1] > arr[mid]) {
                return arr[mid];
            }
            // 如果前面左游标一直往前走到了mid，则left = mid + 1
            if(mid == left){
                left = mid + 1;
                continue;
            }
            if (arr[mid] > arr[left]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arr1 = {6, 7, 1, 2, 3, 4, 5};
        int[] arr2 = {6, 6, 7, 7, 1, 1, 2, 3, 4, 5, 5};
        int[] arr3 = {4,4,4,4,4,4,4,4,4,4
                    ,4,4,1,2,3,4,4,4,4,4
                    ,4,4,4,4,4,4,4,4,4,4
                    ,4,4,4,4,4,4,4,4};
        int[] arr4 = {4,4,4,4,4,4,4,4,4,4
                    ,4,4,4,4,4,4,4,4,4,4
                    ,4,4,4,4,4,4,4,4,4,4
                    ,4,4,1,2,3,4,4,4};
        int[] arr5 = { 4, 1, 2, 2, 2, 2, 2, 2, 2, 2
                    , 3, 3, 3, 3, 3, 3, 3, 3, 3, 4};
        int[] arr6 = {1 , 2, 3, 4, 5, 6};

        System.out.println(findWhileEleRepeat(arr1));
        System.out.println(findWhileEleRepeat(arr2));
        System.out.println(findWhileEleRepeat(arr3));
        System.out.println(findWhileEleRepeat(arr4));
        System.out.println(findWhileEleRepeat(arr5));
        System.out.println(findWhileEleRepeat(arr6));
    }
}
