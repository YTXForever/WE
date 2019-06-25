package lru;

import java.util.HashMap;
import java.util.Map;

/**
 * InnoDB Segment LRU
 *
 * @author yuh
 * @date 2019-06-26 07:35
 **/
public class SegmentLRU {

    private static final boolean IN_NEWLSIT = true;
    private static final boolean IN_OLDLSIT = false;

    class Node {
        Node prev, next;
        int key;
        int value;
        long time;
        boolean list;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.time = System.currentTimeMillis();
            list = IN_OLDLSIT;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Node{");
            sb.append("key=").append(key);
            sb.append(", value=").append(value);
            sb.append('}');
            return sb.toString();
        }
    }


    private Node newHead, oldHead, oldTail;
    private int size;
    private int oldPercent;
    private int newSize;
    private int oldSize;
    private int currNewSize;
    private int currOldSize;
    private long interval;
    private Map<Integer, Node> map = new HashMap<>();

    public SegmentLRU(int size, int oldPercent, int interval) {
        this.size = size;
        this.oldPercent = oldPercent;
        this.oldSize = (int) ((double) oldPercent / 100 * size);
        this.newSize = this.size - this.oldSize;
        this.interval = interval;
    }

    public int get(int key) {
        Node node = map.get(key);
        if (node == null) {
            return -1;
        }
        boolean list = node.list;
        if (list == IN_NEWLSIT) {
            //move to first
            node.prev.next = null;
            node.next.prev = null;
            node.next = node.prev = null;
            node.next = newHead;
            newHead = node;
        } else {
            //if to newListHead
            long time = node.time;
            if (System.currentTimeMillis() - time >= interval) {
                //move to new head
                node.prev.next = null;
                node.next.prev = null;
                node.next = node.prev = null;
                node.next = newHead;
                oldHead = oldHead.prev;
                currOldSize--;
                currNewSize++;
            } else {
                //move to old head
                if (node != oldHead) {
                    node.prev.next = null;
                    node.next.prev = null;
                    node.next = node.prev = null;
                    node.next = oldHead;
                    oldHead = node;
                }
            }
        }


        return node.value;
    }

    public void add(int key, int val) {
        Node node = new Node(key, val);
        map.put(key, node);
        if (currOldSize == oldSize) {
            //remove tail
            Node prev = oldTail.prev;
            oldTail.next.prev = null;
            prev.next = null;
            oldTail = prev;
            currOldSize--;
        }
        if (oldHead == null) {
            oldHead = node;
        }
        if (oldTail == null) {
            oldTail = oldHead;
        }
        currOldSize++;
    }
}
