package com.sunsekey.algorithm.struct;

import com.sunsekey.algorithm.common.BinaryTreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class LevelPrintTree {

    public static void main(String[] args) {
        System.out.println(printFromTopToBottom(BinaryTreeNode.constructMockBT()));
        System.out.println(printFromTopToBottom2(BinaryTreeNode.constructMockBT()));

    }

    /**
     * 利用遍历，需要队列来作辅助，先让root入队，然后进入循环，从队列poll节点。如果左、右节点不为空，则入队
     * 然后继续循环从队列中取出节点
     *
     * @param root
     * @return
     */
    public static ArrayList<Integer> printFromTopToBottom(BinaryTreeNode<Integer> root) {
        ArrayList<Integer> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        Queue<BinaryTreeNode<Integer>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            BinaryTreeNode<Integer> node = queue.poll();
            list.add(node.val);
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
        return list;
    }

    /**
     * 递归
     */
    public static ArrayList<Integer> printFromTopToBottom2(BinaryTreeNode<Integer> root) {
        ArrayList<Integer> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        list.add(root.val);
        levelOrder(root, list);
        return list;
    }

    public static void levelOrder(BinaryTreeNode<Integer> root, ArrayList<Integer> list) {
        if (root == null) {
            return;
        }
        if (root.left != null) {
            list.add(root.left.val);
        }
        if (root.right != null) {
            list.add(root.right.val);
        }
        levelOrder(root.left, list);
        levelOrder(root.right, list);
    }
}
