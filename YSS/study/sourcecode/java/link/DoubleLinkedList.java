package link;

/**
 * 双相链表
 * WARN 双向最好只玩头尾
 * add 如果为空 tail=head head=tail 其他情况注意维护双向
 * remove 如果为一个 tail=head=null 其他情况注意维护双向
 *
 * @author yuh
 * @date 2019-05-28 07:05
 **/
public class DoubleLinkedList {

    class Node {
        Node next;
        Node prev;
        int data;

        Node(Node next, Node prev, int data) {
            this.next = next;
            this.prev = prev;
            this.data = data;
        }

        Node(int data) {
            this(null, null, data);
        }
    }

    private Node head;
    private Node tail;

    private void addFirst(int data) {
        if (head == null) {
            head = new Node(data);
            tail = head;
        } else {
            Node node = new Node(head, null, data);
            head.prev = node;
            head = node;
        }
    }

    private int removeFirst() {
        if (head == null) {
            return Integer.MIN_VALUE;
        }
        if (tail == head) {
            int data = tail.data;
            tail = head = null;
            return data;
        }
        int data = head.data;
        Node next = head.next;
        if (next != null) {
            next.prev = null;
        }
        head.next = null;
        head = next;
        return data;
    }

    private void addTail(int data) {
        if (tail == null) {
            tail = new Node(data);
            head = tail;
        } else {
            Node node = new Node(null, tail, data);
            tail.next = node;
            tail = node;
        }
    }

    private int removeTail() {
        if (tail == null) {
            return Integer.MIN_VALUE;
        }
        if (tail == head) {
            int data = tail.data;
            tail = head = null;
            return data;
        }
        int data = tail.data;
        Node prev = tail.prev;
        if (prev != null) {
            prev.next = null;
        }
        tail.prev = null;
        tail = prev;
        return data;
    }

    public int remove(int data) {
        Node curr = head;
        while (curr != null && curr.data != data) {
            curr = curr.next;
        }
        if (curr == null) {
            return Integer.MIN_VALUE;
        }
        if (curr == head) {
            return removeFirst();
        }
        if (curr == tail) {
            return removeTail();
        }
        int rt = curr.data;
        if (curr.next != null) {
            curr.next.prev = curr.prev;
        }
        if (curr.prev != null) {
            curr.prev.next = curr.next;
        }
        curr.next = curr.prev = null;
        return rt;
    }

    @Override
    public String toString() {
        if (head == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        Node curr = head;
        while (curr != null) {
            sb.append(curr.data);
            sb.append("->");
            curr = curr.next;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
        doubleLinkedList.addFirst(1);
        doubleLinkedList.addFirst(2);
        System.out.println(doubleLinkedList);
        doubleLinkedList.addTail(3);
        doubleLinkedList.addTail(4);
        System.out.println(doubleLinkedList);
        doubleLinkedList.removeFirst();
        System.out.println(doubleLinkedList);
//        doubleLinkedList.removeTail();
//        doubleLinkedList.removeTail();
//        doubleLinkedList.removeTail();
        doubleLinkedList.remove(3);
        System.out.println(doubleLinkedList);

    }
}
