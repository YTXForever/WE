package skiplist;

import java.util.ArrayList;
import java.util.List;

/**
 * 跳表
 *
 * @author yuh
 * @date 2019-06-08 23:48
 **/
public class SkipList<V> {

    class Node<V> {
        int key;
        V val;
        Node left, right, up, down;

        public Node(int key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    private static final int MIN = Integer.MIN_VALUE;
    private static final int MAX = Integer.MAX_VALUE;

    private int size;
    private int height;
    private Node<V> head;
    private Node<V> tail;

    public SkipList() {
        this.height = 1;
        head = new Node<>(MIN, null);
        tail = new Node<>(MAX, null);
        linkHori(head, tail);
        this.size = 0;
    }

    public V get(int key) {
        Node preNode = findPreNode(key);
        if (preNode.key == key) {
            return (V) preNode.val;
        }
        return null;
    }

    public V put(int key, V val) {
        Node preNode = findPreNode(key);
        if (preNode.key == key) {
            //replace
            Object rt = preNode.val;
            preNode.val = val;
            return (V) rt;
        }
        //not found
        Node<V> newNode = new Node<>(key, val);
        insertHorilink(preNode, newNode);
        size++;
        int currHeight = 1;
        while (Math.random() > 0.5) {
            if (currHeight == height) {
                //new layer
                Node<V> newHead = new Node<>(MIN, null);
                Node<V> newTail = new Node<>(MAX, null);
                linkHori(newHead, newTail);
                linkVert(newHead, head);
                linkVert(newTail, tail);
                head = newHead;
                tail = newHead;
                height++;
            }
            while (preNode.up == null) {
                preNode = preNode.left;
            }
            preNode = preNode.up;
            Node<V> upNewNode = new Node<>(key, null);
            linkVert(upNewNode, newNode);
            insertHorilink(preNode, upNewNode);
            newNode = upNewNode;
            currHeight++;
        }
        return null;
    }

    public V remove(int key) {
        Node preNode = findPreNode(key);
        if (preNode.key != key) {
            return null;
        }
        Object val = preNode.val;
        //remove
        removeNode(preNode);
        return (V) val;
    }

    public List<V> tail(int k1, int k2) {
        ArrayList<V> list = new ArrayList<>();
        Node preNode = findPreNode(k1);
        while (preNode.key < k1) {
            preNode = preNode.right;
        }
        while (preNode.key <= k2) {
            list.add((V) preNode.val);
        }
        return list;
    }

    public void print() {
        Node curr = head;
        while (curr != null) {
            Node down = curr.down;
            System.out.print("head->");
            curr = curr.right;
            while (curr.key != tail.key) {
                System.out.print(curr.key + "->");
            }
            System.out.print("tail");
            System.out.println();
            curr = down;
        }
    }

    private void removeNode(Node preNode) {
        while (preNode != null) {
            Node up = preNode.up;
            deHoriLink(preNode);
            deVertiAll(preNode);
            preNode = up;
        }
    }

    private void deVertiAll(Node node) {
        node.up = node.down = null;
    }

    private void deHoriLink(Node node) {
        node.left.right = node.right;
        node.right.left = node.left;
    }

    private void linkVert(Node up, Node down) {
        up.down = down;
        down.up = up;
    }

    private void insertHorilink(Node preNode, Node<V> newNode) {
        Node right = preNode.right;
        preNode.right = newNode;
        newNode.left = preNode;
        right.left = newNode;
        newNode.right = right;
    }


    private void linkHori(Node left, Node right) {
        left.right = right;
        right.left = left;
    }


    private Node findPreNode(int key) {
        Node curr = head;
        while (true) {
            while (curr.key != tail.key && curr.right.key <= key) {
                curr = curr.right;
            }
            if (curr.down != null) {
                curr = curr.down;
            } else {
                break;
            }
        }
        return curr;
    }


    public static void main(String[] args) {
        SkipList<Integer> skipList = new SkipList<>();
        for (int i = 0; i < 5; i++) {
            skipList.put(i, i);
        }
        for (int i = 0; i < 5; i++) {
            System.out.println(skipList.get(i));
        }
    }
}
