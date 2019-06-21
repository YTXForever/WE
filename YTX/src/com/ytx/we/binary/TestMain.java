package com.ytx.we.binary;

public class TestMain {
    public static void main(String[] args) {
        BinaryTree bt = new BinaryTree();
        bt.insert(10,"10");
        bt.insert(30,"30");
        bt.insert(3,"3");
        bt.insert(90,"90");
        bt.insert(20,"20");
        bt.insert(89,"89");
        bt.search();
        System.out.println();
        BinaryTreeNode findNode = bt.find(10);
        System.out.println(findNode);
        System.out.println("========pre========");
        bt.preOrder();
        System.out.println();
        System.out.println("=========level=======");
        bt.levelOrder();
        System.out.println();
        System.out.println("=========removemin=======");
        bt.removeMin();
        bt.levelOrder();

        System.out.println();
        System.out.println("=========removemax1=======");
        bt.removeMax();
        bt.levelOrder();

        System.out.println();
        System.out.println("=========remove=======");
        bt.insert(3,"3");
        bt.remove(10);
        bt.levelOrder();


    }
}
