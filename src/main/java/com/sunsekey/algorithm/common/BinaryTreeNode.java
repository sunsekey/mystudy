package com.sunsekey.algorithm.common;

public class BinaryTreeNode<T> {

    public T val;
    public BinaryTreeNode<T> left;
    public BinaryTreeNode<T> right;

    public BinaryTreeNode(){

    }

    public BinaryTreeNode(BinaryTreeNode<T> left, BinaryTreeNode<T> right, T val){
        this.left = left;
        this.right = right;
        this.val = val;
    }

    public static BinaryTreeNode<Integer> constructMockBT(){
        BinaryTreeNode<Integer> subSubRightNode1 = new BinaryTreeNode<>(null, null, 8);
        BinaryTreeNode<Integer> subSubLeftNode1 = new BinaryTreeNode<>(null, null, 7);
        BinaryTreeNode<Integer> subSubLeftNode2 = new BinaryTreeNode<>(null, null, 10);
        BinaryTreeNode<Integer> subLeftNode1 = new BinaryTreeNode<>(subSubLeftNode1, null, 4);
        BinaryTreeNode<Integer> subLeftNode2 = new BinaryTreeNode<>(null, subSubLeftNode2, 5);
        BinaryTreeNode<Integer> subRightNode1 = new BinaryTreeNode<>(subSubRightNode1, null, 6);
        BinaryTreeNode<Integer> leftNode = new BinaryTreeNode<>(subLeftNode1, subLeftNode2, 2);
        BinaryTreeNode<Integer> rightNode = new BinaryTreeNode<>(subRightNode1, null, 3);
        return new BinaryTreeNode<>(leftNode, rightNode, 1);
    }

}
