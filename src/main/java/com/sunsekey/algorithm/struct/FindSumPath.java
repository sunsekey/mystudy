package com.sunsekey.algorithm.struct;

import com.sunsekey.algorithm.common.BinaryTreeNode;

import java.util.ArrayList;

/**
 * 输入一颗二叉树和一个整数，打印出二叉树中结点值的和为输入整数 的所有路径。路径定义为从树的根结点开始往下一直到叶结点所经过的结点形成一 条路径。
 */
public class FindSumPath {

    private static ArrayList<ArrayList<Integer>> listAll = new ArrayList<>();
    private static ArrayList<Integer> list = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println(findPath(BinaryTreeNode.constructMockBT(), 18));
    }

    /**
     * 其中listAll为结果集、
     * list为当前路径（通过递归生成路径，如果到叶子节点了，并且target减为0了，则加到结果集中）
     * 关键步骤是回退，到叶子节点了，不管是否满足条件，都是要回退的，因为要一步步回退回去找另外的路径
     *
     * @param root
     * @param target
     * @return
     */
    public static ArrayList<ArrayList<Integer>> findPath(BinaryTreeNode<Integer> root, int target) {
        if (root == null) {
            return listAll;
        }
        list.add(root.val);
        target -= root.val;
        if (target == 0 && root.left == null && root.right == null) {
            listAll.add(new ArrayList<>(list));
        }
        findPath(root.left, target);
        findPath(root.right, target); // 回退
        list.remove(list.size() - 1);
        return listAll;
    }
}
