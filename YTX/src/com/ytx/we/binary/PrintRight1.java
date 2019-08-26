package com.ytx.we.binary;

import java.util.ArrayList;
import java.util.List;


/**
 * For YTX
 * 2019-07-23 19:18
 */
public class PrintRight1 {

    static class Node {
        int data;
        Node left, right;

        public Node(int data) {
            this.data = data;
        }
    }

    public static void printRight(Node head) {
        List<Integer> list = new ArrayList<>();
        _printRight(head,0, list);
        for (Integer value : list) {
            System.out.println(value);
        }
    }

    private static void _printRight(Node head,int layer, List<Integer> list) {
        if(head == null){
            return;
        }
        if(layer>=list.size()){
            list.add(head.data);
        }
        _printRight(head.right,layer+1,list);
        _printRight(head.left,layer+1,list);

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
