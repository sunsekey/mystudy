package com.sunsekey.algorithm.struct;

/**
 * 输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。
 * 假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
 * 例如输入前序遍历序列 {1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}，
 * 则重建二叉树并返回
 */
public class ReconstructBinaryTree {

    public static void main(String[] args) {
        int[] preorder = {1,2,4,7,3,5,6,8};
        int[] inorder = {4,7,2,1,5,3,8,6};
        reConstructBinaryTree(preorder,inorder);

    }

    /**
     * 传入子数组的边界索引
     * @param preorder
     * @param inorder
     * @return
     */
    public static TreeNode reConstructBinaryTree(int[] preorder, int[] inorder) {
        if (preorder == null || preorder.length == 0 || inorder == null || inorder.length == 0) return null;
        return helper(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    private static TreeNode helper(int[] preorder, int preL, int preR, int[] inorder, int inL, int inR) {
        if (preL > preR || inL > inR) {
            return null;
        }
        int rootVal = preorder[preL];
        int index = 0;
        while (index <= inR && inorder[index] != rootVal) {
            index++;
        }
        TreeNode root = new TreeNode(rootVal);
        // TODO 为什么preL - inL 再 + index?
        root.left = helper(preorder, preL + 1, preL - inL + index, inorder, inL, index);
        root.right = helper(preorder, preL - inL + index + 1, preR, inorder, index + 1, inR);
        return root;
    }

    private static class TreeNode{
        private int value;
        private TreeNode left;
        private TreeNode right;
        private TreeNode(int value){
            this.value = value;
        }

        public TreeNode getLeft() {
            return left;
        }

        public void setLeft(TreeNode left) {
            this.left = left;
        }

        public TreeNode getRight() {
            return right;
        }

        public void setRight(TreeNode right) {
            this.right = right;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}
