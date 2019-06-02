package hash;


/**
 * 简单HashMap实现
 *
 * @author yuh
 * @date 2019-06-02 07:26
 **/
public class MyHashMap<K, V> {

    class Node<K, V> {
        K key;
        V val;
        int hash;
        Node<K, V> next;

        public Node(K key, V val, int hash) {
            this.key = key;
            this.val = val;
            this.hash = hash;
        }

    }

    private Node<K, V>[] table;
    private int capacity = 1 << 4;
    private int thr = (int) (capacity * 0.75);
    private int size = 0;

    public MyHashMap() {
        table = (Node<K, V>[]) new Node[capacity];
    }

    public V put(K key, V val) {
        int hash = hash(key);
        int index = index(hash);
        Object rt = null;
        Node first = table[index];
        if (first == null) {
            table[index] = new Node(key, val, hash);
            size++;
        } else if (first.hash == hash && first.key.equals(key)) {
            table[index] = new Node(key, val, hash);
            rt = first.val;
        } else {
            while (true) {
                Node next = first.next;
                if (next == null) {
                    first.next = new Node(key, val, hash);
                    size++;
                    break;
                } else if (next.hash == hash && next.key.equals(key)) {
                    rt = next.val;
                    first.next = new Node(key, val, hash);
                    break;
                }
                first = first.next;
            }
        }
        if (size == thr) {
            resize();
        }
        return (V) rt;
    }


    public V get(K key) {
        int hash = hash(key);
        int index = index(hash);
        Node first = table[index];
        if (first == null) {
            return null;
        }
        if (first.hash == hash && first.key.equals(key)) {
            return (V) first.val;
        } else {
            first = first.next;
            while (first != null) {
                if (first.hash == hash && first.key.equals(key)) {
                    return (V) first.val;
                }
                first = first.next;
            }
            return null;
        }
    }

    public V remove(K key) {
        int hash = hash(key);
        int index = index(hash);
        Node first = table[index];
        if (first == null) {
            return null;
        }
        if (first.hash == hash && first.key.equals(key)) {
            table[index] = null;
            size--;
            return (V) first.val;
        } else {
            while (first.next != null) {
                Node next = first.next;
                if (next.hash == hash && next.key.equals(key)) {
                    first.next = next.next;
                    size--;
                    return (V) next.val;
                }
                first = first.next;
            }
            return null;
        }
    }

    private void resize() {
        int newThr = thr << 1;
        int newCap = capacity << 1;
        Node<K, V>[] newTab = (Node<K, V>[]) new Node[newCap];
        for (int i = 0; i < capacity; i++) {
            Node<K, V> kvNode = table[i];
            if (kvNode == null) {
                continue;
            }
            Node<K, V> dummyLO = new Node<>(null, null, 0);
            Node<K, V> dummyHi = new Node<>(null, null, 0);
            Node currLO = dummyLO;
            Node currLHi = dummyHi;

            while (kvNode != null) {
                if ((kvNode.hash & capacity) == 0) {
                    currLO.next = kvNode;
                    currLO = currLO.next;
                } else {
                    currLHi.next = kvNode;
                    currLHi = currLHi.next;
                }
                kvNode = kvNode.next;
            }
            newTab[i] = dummyLO.next;
            newTab[i + capacity] = dummyHi.next;
        }
        table = newTab;
        capacity = newCap;
        thr = newThr;
    }

    private int index(int hash) {
        return hash & (capacity - 1);
    }

    private int hash(K key) {
        int h = key.hashCode();
        return h ^ h >>> 16;
    }


    public static void main(String[] args) {
        MyHashMap<String, String> map = new MyHashMap<>();
        for (int i = 0; i < 100; i++) {
            map.put(i + "", i + "");
        }

        map.remove(30+"");

        for (int i = 0; i < 10; i++) {
            String s = map.get(i * 10 + "");
            System.out.println(s);
        }
    }

}
