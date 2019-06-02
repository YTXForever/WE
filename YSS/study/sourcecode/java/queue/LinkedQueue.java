package queue;

/**
 * @author yuh
 * @date 2019-05-31 07:06
 **/
public class LinkedQueue<T> {

    class Node {
        Node next;
        Node prev;
        T data;

        Node(Node next, Node prev, T data) {
            this.prev = prev;
            this.next = next;
            this.data = data;
        }

        Node(T data) {
            this(null, null, data);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            Node curr = this;
            while (curr != null) {
                sb.append(curr.data).append("<->");
                curr = curr.next;
            }
            return sb.toString();
        }
    }

    private Node head;
    private Node tail;


    public void enqueue(T e) {
        if (tail == null) {
            tail = new Node(e);
            head = tail;
            return;
        }
        Node node = new Node(e);
        node.prev = tail;
        tail.next = node;
        tail = node;
    }

    public T dequeue() {
        if (head == null) {
            return null;
        }
        if (head == tail) {
            T data = head.data;
            head = tail = null;
            return data;
        }
        T data = head.data;
        Node next =head.next;
        head.next = null;
        next.prev = null;
        head = next;
        return data;
    }

    public static void main(String[] args) {
        LinkedQueue<Integer> queue = new LinkedQueue<>();
        for (int i = 0; i < 5; i++) {
            queue.enqueue(i);
        }

        for (int i = 0; i < 10; i++) {
            System.out.println(queue.dequeue());
        }
    }
}
