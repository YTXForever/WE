package tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author yuh
 * @date 2019-06-03 06:51
 **/
public class AVLTree<K extends Comparable<K>, V> {

    private static class Node<K extends Comparable<K>, V> {
        K key;
        V val;
        Node left;
        Node right;
        int height = 1;

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
        //AVL
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        int i = balanceFactor(node);
        //左边的左边
        if (i > 1 && balanceFactor(node.left) > 0) {
            node = rotateRight(node);
        }
        //左边的右边
        if (i > 1 && balanceFactor(node.left) < 0) {
            node.left = rotateLeft(node.left);
            node = rotateRight(node);
        }
        //右边的左边
        if (i < -1 && balanceFactor(node.right) > 0) {
            node.right = rotateRight(node.right);
            node = rotateLeft(node);
        }
        //右边的右边
        if (i < -1 && balanceFactor(node.right) < 0) {
            node = rotateLeft(node);
        }
        //AVL
        return node;
    }


    private Node rotateRight(Node node) {
        Node left = node.left;
        node.left = left.right;
        left.right = node;

        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        left.height = Math.max(getHeight(left.left), getHeight(left.right)) + 1;
        return left;
    }


    public Node rotateLeft(Node node) {
        Node right = node.right;
        right.left = node.right;
        right.left = node;

        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        right.height = Math.max(getHeight(right.left), getHeight(right.right)) + 1;
        return right;
    }

    private boolean isBalance() {
        return _isBalance(root);
    }

    private boolean _isBalance(Node node) {
        if (node == null) {
            return true;
        }
        return Math.abs(balanceFactor(node)) <= 1 && _isBalance(node.left) && _isBalance(node.right);
    }

    private int getHeight(Node node) {
        return node == null ? 0 : node.height;
    }

    private int balanceFactor(Node node) {
        if (node == null) {
            return 0;
        }
        return getHeight(node.left) - getHeight(node.right);
    }

    private boolean isBST() {
        List<Node> nodeArrayList = inOrder();
        for (int i = 0; i < nodeArrayList.size() - 1; i++) {
            if (nodeArrayList.get(i).key.compareTo(nodeArrayList.get(i + 1).key) > 0) {
                return false;
            }
        }
        return true;
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
                node.right = removeNode(node.right, (K) minNode.key);
                minNode.left = node.left;
                minNode.right = node.right;
                node.left.right = null;
                node = minNode;
            }
        }


        //AVL
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        int i = balanceFactor(node);
        //左边的左边
        if (i > 1 && balanceFactor(node.left) > 0) {
            node = rotateRight(node);
        }
        //左边的右边
        if (i > 1 && balanceFactor(node.left) < 0) {
            node.left = rotateLeft(node.left);
            node = rotateRight(node);
        }
        //右边的左边
        if (i < -1 && balanceFactor(node.right) > 0) {
            node.right = rotateRight(node.right);
            node = rotateLeft(node);
        }
        //右边的右边
        if (i < -1 && balanceFactor(node.right) < 0) {
            node = rotateLeft(node);
        }
        //AVL
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
        AVLTree<Integer, Integer> stringBST = new AVLTree<>();
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
//        System.out.println(stringBST.isBST());
//        System.out.println(stringBST.isBalance());

        for (int i = 0; i < 10; i++) {
            stringBST.put(i, i);
        }
    }

}
