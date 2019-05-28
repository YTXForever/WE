package link;

/**
 * 实现单链表
 * 采用虚拟头结点方式实现
 * WARN 单链表最好只玩头 否则O(n)
 *
 * @author yuh
 * @date 2019-05-28 06:35
 **/
public class SingleLinkedList {

    class Node {
        Node next;
        int data;

        Node(Node next, int data) {
            this.next = next;
            this.data = data;
        }

        Node(int data) {
            this(null, data);
        }
    }

    private Node dummyHead = new Node(null, Integer.MIN_VALUE);

    public void addTail(int data) {
        Node curr = dummyHead;
        while (curr.next != null) {
            curr = curr.next;
        }
        curr.next = new Node(data);
    }

    public int removeTail() {
        Node prev = dummyHead;
        while (prev.next != null) {
            if (prev.next.next == null) {
                Node next = prev.next;
                prev.next = null;
                return next.data;
            }
            prev = prev.next;
        }
        return Integer.MIN_VALUE;
    }

    public int removeHead() {
        Node next = dummyHead.next;
        if (next == null) {
            return Integer.MIN_VALUE;
        }
        dummyHead.next = next.next;
        return next.data;
    }

    public void addHead(int data) {
        dummyHead.next = new Node(dummyHead.next, data);
    }

    public int remove(int data) {
        Node curr = dummyHead;
        while (curr.next != null && curr.next.data != data) {
            curr = curr.next;
        }
        if (curr.next == null) {
            return Integer.MIN_VALUE;
        }
        Node next = curr.next;
        curr.next = curr.next.next;
        return next.data;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Node curr = dummyHead.next;
        while (curr != null) {
            stringBuilder.append(curr.data).append("->");
            curr = curr.next;
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        SingleLinkedList singleLinkedList = new SingleLinkedList();
        singleLinkedList.addHead(1);
        singleLinkedList.addHead(2);
        singleLinkedList.addHead(3);
        System.out.println(singleLinkedList);
        singleLinkedList.addTail(4);
        singleLinkedList.addTail(5);
        singleLinkedList.addTail(6);
        System.out.println(singleLinkedList);
        singleLinkedList.removeHead();
        singleLinkedList.removeHead();
        System.out.println(singleLinkedList);
        singleLinkedList.removeTail();
        singleLinkedList.removeTail();
        System.out.println(singleLinkedList);
        singleLinkedList.remove(4);
        System.out.println(singleLinkedList);

    }
}
