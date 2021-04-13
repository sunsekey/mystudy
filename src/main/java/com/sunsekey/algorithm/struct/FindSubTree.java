package com.sunsekey.algorithm.struct;

import com.sunsekey.algorithm.common.BinaryTreeNode;

/**
 * 输入两棵二叉树 A，B，判断 B 是不是 A 的子结构。（ps：我们约定空 树不是任意一个树的子结构）
 */
public class FindSubTree {

    /**
     * 若根节点相等，利用递归比较他们的子树是否相等，若根节点不相等，则利用递归分别在左右子树中查找，递归的递归
     * @param source
     * @param target
     * @return
     */
    public boolean hasSubTree(BinaryTreeNode<Integer> source, BinaryTreeNode<Integer> target) {
        if (target == null) {
            return true;
        }
        if (source == null) {
            return false;
        }
        if (doesTree1HaveTree2(source, target)) {
            return true;
        }
        return hasSubTree(source.left, target) || hasSubTree(source.right, target);
    }

    public static boolean doesTree1HaveTree2(BinaryTreeNode source, BinaryTreeNode target) {
        if (source == null && target == null) {
            return true;
        }
        if (source == null || target == null) {
            return false;
        }
        if (source.val != target.val) {
            return false;
        }
        return doesTree1HaveTree2(source.left, target.left) && doesTree1HaveTree2(source.right, target.right);
    }
}
