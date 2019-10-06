package com.ytx.we.binary;

import java.util.TreeMap;

/**
 * For YTX
 * 2019-07-23 19:18
 */
public class PrintRight {

    static class Node {
        int data;
        Node left, right;

        public Node(int data) {
            this.data = data;
        }
    }

    public static void printRight(Node head) {
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        _printRight(head, 0, treeMap);
        for (Integer value : treeMap.values()) {
            System.out.println(value);
        }
    }

    private static void _printRight(Node head, int layer, TreeMap<Integer, Integer> treeMap) {
        if(head == null){
            return;
        }
        treeMap.put(layer,head.data);
        _printRight(head.left,layer+1,treeMap);
        _printRight(head.right,layer+1,treeMap);
    }

    public static void main(String[] args) {
        Node node1 = new Node(10);
        Node node2 = new Node(8);
        Node node3 = new Node(20);
        Node node4 = new Node(5);
        Node node5 = new Node(7);
        Node node6 = new Node(15);
        Node node7 = new Node(6);



        /*
         *
         *             10
         *            /  \
         *           8    20
         *          / \   /
         *         5  7 15
         *           /
         *          6
         *
         */

        node1.left = node2;
        node1.right = node3;

        node2.left = node4;
        node2.right = node5;
        node3.left = node6;

        node5.left = node7;
        printRight(node1);


    }
}
