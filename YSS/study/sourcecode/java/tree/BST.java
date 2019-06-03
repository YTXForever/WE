package tree;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author yuh
 * @date 2019-06-03 06:51
 **/
public class BST<K extends Comparable<K>, V> {

    private static class Node<K extends Comparable<K>, V> {
        K key;
        V val;
        Node left;
        Node right;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    private Node root;

    public void put(K key, V val) {
        root = putVal(root, key, val);
    }

    private Node putVal(Node node, K key, V val) {
        if (node == null) {
            return new Node(key, val);
        }
        int res = node.key.compareTo(key);
        if (res > 0) {
            node.left = putVal(node.left, key, val);
        } else if (res < 0) {
            node.right = putVal(node.right, key, val);
        } else {
            node.val = val;
        }
        return node;
    }


    public V get(K key) {
        Node node = getNode(root, key);
        return node == null ? null : (V) node.val;
    }

    private Node getNode(Node node, K key) {
        if (node == null) {
            return null;
        }
        int res = node.key.compareTo(key);
        if (res > 0) {
            return getNode(node.left, key);
        } else if (res < 0) {
            return getNode(node.right, key);
        } else {
            return node;
        }
    }

    public List<Node> preOrder() {
        ArrayList<Node> ks = new ArrayList<>();
        _preOrder(root, ks);
        return ks;
    }

    private void _preOrder(Node node, ArrayList<Node> ks) {
        if (node == null) {
            return;
        }
        ks.add(node);
        _preOrder(node.left, ks);
        _preOrder(node.right, ks);
    }

    public List<Node> inOrder() {
        ArrayList<Node> ks = new ArrayList<>();
        _inOrder(root, ks);
        return ks;
    }

    private void _inOrder(Node node, ArrayList<Node> ks) {
        if (node == null) {
            return;
        }
        _inOrder(node.left, ks);
        ks.add(node);
        _inOrder(node.right, ks);
    }

    public List<Node> postOrder() {
        ArrayList<Node> ks = new ArrayList<>();
        _postOrder(root, ks);
        return ks;
    }

    private void _postOrder(Node node, ArrayList<Node> ks) {
        if (node == null) {
            return;
        }
        _postOrder(node.left, ks);
        _postOrder(node.right, ks);
        ks.add(node);
    }

    public V min() {
        Node node = getMinNode(root);
        return node == null ? null : (V) node.val;
    }

    private Node getMinNode(Node node) {
        if (node == null) {
            return null;
        }
        return node.left == null ? node : getMinNode(node.left);
    }

    public V max() {
        Node node = getMaxNode(root);
        return node == null ? null : (V) node.val;
    }

    private Node getMaxNode(Node node) {
        if (node == null) {
            return null;
        }
        return node.right == null ? node : getMaxNode(node.right);
    }

    public Node successor(K key) {
        Node node = getNode(root, key);
        if (node == null) {
            return null;
        }
        return getMinNode(node.right);
    }

    public Node predesuccessor(K key) {
        Node node = getNode(root, key);
        if (node == null) {
            return null;
        }
        return getMaxNode(node.left);
    }

    public void removeMin() {
        root = removeMinNode(root);
    }

    private Node removeMinNode(Node node) {
        if (node == null) {
            return null;
        }
        if (node.left != null) {
            node.left = removeMinNode(node.left);
        } else {
            return node.right;
        }
        return node;
    }

    public void removeMax() {
        root = removeMaxNode(root);
    }

    private Node removeMaxNode(Node node) {
        if (node == null) {
            return null;
        }
        if (node.right != null) {
            node.right = removeMaxNode(node.right);
        } else {
            return node.left;
        }
        return node;
    }


    public void remove(K key) {
        root = removeNode(root, key);
    }

    private Node removeNode(Node node, K key) {
        if (node == null) {
            return null;
        }

        int res = node.key.compareTo(key);
        if (res > 0) {
            node.left = removeNode(node.left, key);
        } else if (res < 0) {
            node.right = removeNode(node.right, key);
        } else {
            if (node.left == null) {
                node = node.right;
            } else if (node.right == null) {
                node = node.left;
            } else {
                Node minNode = getMinNode(node.right);
                removeMinNode(node.right);
                minNode.left = node.left;
                minNode.right = node.right;
                node.left.right = null;
                node = minNode;
            }
        }
        return node;
    }

    public int deepth() {
        return _deepth(root);
    }

    private int _deepth(Node node) {
        if (node == null) {
            return 0;
        }
        return Math.max(_deepth(node.left), _deepth(node.right)) + 1;
    }


    public static void main(String[] args) {
        BST<Integer, Integer> stringBST = new BST<>();
//        List<Integer> list = new ArrayList<>();
//        Random random = new Random();
//        for (int i = 0; i < 5; i++) {
//            int nextInt = Math.abs(random.nextInt(50));
//            list.add(nextInt);
//            stringBST.put(nextInt, nextInt);
//        }
//        for (Integer k : list) {
//            System.out.println(k + "->" + stringBST.get(k));
//        }
//        System.out.println(list);
//        List<Node> nodes = stringBST.inOrder();
//        for (Node node : nodes) {
//            System.out.println(node.key + "->" + node.val);
//        }
//        System.out.println("===============");
//        System.out.println(stringBST.min());
//        System.out.println(stringBST.max());

//        stringBST.remove(list.get(2));
//        System.out.println("===============");
//        System.out.println(stringBST.min());
//        System.out.println(stringBST.max());
//        System.out.println("============");

//        System.out.println("================");
//        nodes = stringBST.inOrder();
//        for (Node node : nodes) {
//            System.out.println(node.key + "->" + node.val);
//        }
//        System.out.println("============");

//        stringBST.put(1,1);
//        stringBST.put(-1,-1);
//        stringBST.put(2,2);
//        stringBST.put(3,3);
//        System.out.println(stringBST.deepth());
    }

}
