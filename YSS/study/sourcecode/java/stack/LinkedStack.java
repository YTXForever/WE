package stack;

import link.LinkTest;

/**
 * @author yuh
 * @date 2019-05-30 06:58
 **/
public class LinkedStack<T> {

    class Node {
        Node next;
        T data;

        Node(Node next, T data) {
            this.next = next;
            this.data = data;
        }

        Node(T data) {
            this(null, data);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            Node curr = this;
            while (curr != null) {
                sb.append(curr.data).append("->");
                curr = curr.next;
            }
            return sb.toString();
        }
    }

    private Node head;

    public void push(T ele){
        if(head == null){
            head = new Node(ele);
            return;
        }
        head = new Node(head,ele);
    }

    public T pop(){
        if(head == null){
            return null;
        }
        Node rt = head;
        head = head.next;
        return rt.data;
    }

    public static void main(String[] args) {
        LinkedStack<String> stack = new LinkedStack<>();
        for (int i = 0; i < 5; i++) {
            stack.push(i+"");
        }
        for (int i = 0; i < 5; i++) {
            System.out.println(stack.pop());
        }
    }
}
