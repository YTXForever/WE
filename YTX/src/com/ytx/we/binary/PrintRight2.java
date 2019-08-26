package com.ytx.we.binary;

import java.util.LinkedList;
import java.util.TreeMap;

public class PrintRight2 {

    static class Node {
        Node left;
        Node right;
        int data;

        public Node(int data) {
            this.data = data;
        }

        public Node() {
        }
    }

    static class NodeExt extends Node {
        int layer;

        public NodeExt(Node node, int layer) {
            this.layer = layer;
            this.left = node.left;
            this.right = node.right;
            this.data = node.data;
        }
    }


    public static void print(PrintRight2.Node root) {
        TreeMap<Integer, PrintRight2.NodeExt> map = new TreeMap<>();


        LinkedList<PrintRight2.NodeExt> queue = new LinkedList<>();
        queue.addLast(new PrintRight2.NodeExt(root, 0));
        while (queue.size() != 0) {
            PrintRight2.NodeExt node = queue.removeFirst();
            map.put(node.layer, node);
            if (node.left != null) {
                queue.addLast(new PrintRight2.NodeExt(node.left, node.layer + 1));
            }
            if (node.right != null) {
                queue.addLast(new PrintRight2.NodeExt(node.right, node.layer + 1));
            }
        }


        map.forEach((k, v) -> {
            System.out.println(v.data);
        });
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
        print(node1);


    }
}