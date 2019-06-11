package tree.lca;

import tree.BST;

/**
 * @author yuh
 * @date 2019-06-11 18:47
 **/
public class LCA {

    static class Node {
        int data;
        Node left;
        Node right;


        public Node(int data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return data+"";
        }
    }

    public static Node lca(Node root, Node p, Node q) {
        if (root == null) {
            //p q 都没找到
            return null;
        }
        if (root == p || root == q) {
            //找到了p 或者 q
            return root;
        }
        Node left = lca(root.left, p, q);
        Node right = lca(root.right, p, q);
        if (left != null && right != null) {
            //一面找到一个 因为节点是不重复的
            return root;
        }
        //两个在一面
        if (left != null) {
            //在左面
            return left;
        }
        if (right != null) {
            //在右边
            return right;
        }
        //两面都没有
        return null;
    }

    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);

        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node3.right = node5;

        System.out.println(lca(node1,node2,node4));
    }
}
