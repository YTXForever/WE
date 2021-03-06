package bst;

public class BST1 {

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

    public static void main(String[] args) {
        String[] techerDate = {"28", "30", "16", "13", "42", "29", "13"};

        BST1 bst = new BST1();
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
