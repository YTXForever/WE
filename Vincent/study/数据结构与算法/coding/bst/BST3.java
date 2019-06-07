package bst;

import java.util.LinkedList;

public class BST3 {

    private Node root;

    public void add(String key, String value) {
        root = add(root, key, value);
    }

    private Node add(Node node, String key, String value) {
        if (node == null) {
            return new Node(key, value);
        }
        if (key.compareTo(node.key) < 0) {
            node.left = add(node.left, key, value);
        } else if (key.compareTo(node.key) > 0) {
            node.right = add(node.right, key, value);
        } else {
            node.value = value;
        }
        return node;
    }

    public void preForEach() {
        preForEach(root);
    }

    private void preForEach(Node node) {
        if (node != null) {
            preForEach(node.left);
            System.out.println("key = " + node.key + ", value = " + node.value);
            preForEach(node.right);
        }
    }

    public String search(String key) {
        return search(root, key);
    }

    private String search(Node node, String key) {
        if (node == null) {
            return null;
        }
        if (key.compareTo(node.key) < 0) {
            return search(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            return search(node.right, key);
        } else {
            return node.value;
        }
    }

    public boolean contains(String key) {
        return contains(root, key);
    }

    private boolean contains(Node node, String key) {
        if (node == null) {
            return false;
        }
        if (key.compareTo(node.key) < 0) {
            return contains(node.left, key);
        } else if (key.compareTo(node.key) > 0) {
            return contains(node.right, key);
        } else {
            return true;
        }
    }

    public void breadthForEach() {
        LinkedList<String> queue = new LinkedList<String>();
        queue.addLast(root.key);
        breadthForEach(root, queue);
        for (String element : queue) {
            System.out.print(element + " ");
        }
    }

    private void breadthForEach(Node node, LinkedList<String> queue) {
        if (node == null) {
            return;
        }
        if (node.left != null) {
            queue.addLast(node.left.key);
        }
        if (node.right != null) {
            queue.addLast(node.right.key);
        }
        breadthForEach(node.left, queue);
        breadthForEach(node.right, queue);
    }

    public void breadthForEach1() {
        LinkedList<Node> queue = new LinkedList<Node>();
        queue.addLast(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            System.out.print(node.key + " ");

            if (node.left != null) {
                queue.addLast(node.left);
            }
            if (node.right != null) {
                queue.addLast(node.right);
            }
        }
    }

    public String minKey() {
        return minKey(root).key;
    }

    private Node minKey(Node node) {
        if (node.left == null) {
            return node;
        }
        return minKey(node.left);
    }

    public String maxKey() {
        return maxKey(root).key;
    }

    private Node maxKey(Node node) {
        if (node.right == null) {
            return node;
        }
        return maxKey(node.right);
    }

    public void removeMin() {
        root = removeMin(root);
    }

    private Node removeMin(Node node) {
        if (node.left == null) {
            return node.right;
        }
        node.left = removeMin(node.left);
        return node;
    }

    public void removeMax() {
        root = removeMax(root);
    }

    private Node removeMax(Node node) {
        if (node.right == null) {
            return node.left;
        }
        node.right = removeMax(node.right);
        return node;
    }

    public void remove(String key) {
        root = remove(root, key);
    }

    private Node remove(Node node, String key) {
        if (key.compareTo(node.key) < 0) {
            node.left = remove(node.left, key);
            return node;
        } else if (key.compareTo(node.key) > 0) {
            node.right = remove(node.right, key);
            return node;
        } else {
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }

            Node rightMin = minKey(node.right);
            rightMin.right = removeMin(node.right);
            rightMin.left = node.left;
            return rightMin;
        }
    }


    public static void main(String[] args) {
        String[] techerDate = {"28", "30", "16", "13", "42", "29", "13", "22"};

        BST3 bst = new BST3();
        for (int i = 0; i < techerDate.length; i++) {
            bst.add(techerDate[i], i + "");
        }

        bst.preForEach();

        String search = bst.search("42");
        System.out.println(search);

        boolean contains = bst.contains("43");
        System.out.println(contains);

        boolean contains1 = bst.contains("42");
        System.out.println(contains1);

        bst.breadthForEach();
        System.out.println();

        bst.breadthForEach1();
        System.out.println();

        System.out.println("最小值：" + bst.minKey());
        System.out.println("最大值：" + bst.maxKey());

//        bst.removeMin();
//        bst.preForEach();
//
//        System.out.println("删除最大元素后");
//        bst.removeMax();
//        bst.preForEach();

        bst.remove("16");
        bst.preForEach();

    }


    private class Node {
        private String key;
        private String value;
        private Node left;
        private Node right;

        public Node(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }
    }
}
