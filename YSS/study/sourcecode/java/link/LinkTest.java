package link;

import java.util.Objects;

/**
 * 链表综合测试
 *
 * @author yuh
 * @date 2019-05-29 07:14
 **/
public class LinkTest {

    static class Node {
        Node next;
        int data;

        Node(Node next, int data) {
            this.next = next;
            this.data = data;
        }

        Node(int data) {
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

    //反转单链表
    public static Node reverseNode(Node head) {
        Node newHead = null;
        while (head != null) {
            Node next = head.next;
            head.next = newHead;
            newHead = head;
            head = next;
        }
        return newHead;
    }

    //查看链表的中间节点
    public static Node midNode(Node node) {
        if (node == null) {
            return null;
        }
        Node fast = node;
        Node slow = node;

        while (fast.next != null && fast.next.next!=null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    //hasCircle
    public static boolean hasCircle(Node node) {
        if (node == null) {
            return false;
        }
        Node fast = node;
        Node slow = node;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }

    //合并两个有序链表
    public static Node merge(Node head1, Node head2) {
        Node dummyHead = new Node(null, 0);
        Node curr = dummyHead;
        if (head1 == null) {
            return head2;
        }
        if (head2 == null) {
            return head1;
        }
        while (head1 != null || head2 != null) {
            if (head1 == null) {
                curr.next = head2;
                head2 = head2.next;
            } else if (head2 == null) {
                curr.next = head1;
                head1 = head1.next;
            } else if (head1.data < head2.data) {
                curr.next = head1;
                head1 = head1.next;
            } else {
                curr.next = head2;
                head2 = head2.next;
            }
            curr = curr.next;
        }
        return dummyHead.next;
    }

    //删除倒数第K个节点
    public static Node delLastKNode(int k, Node head) {
        int i = 0;
        Node fast = head;
        while (i < k && fast != null) {
            i++;
            fast = fast.next;
        }
        if (fast == null) {
            return head;
        }

//        System.out.println("前部锚定节点:" + fast.data);
        Node curr = head;
        Node prev = null;
        while(fast != null){
            prev = curr;
            curr = curr.next;
            fast = fast.next;
        }
        prev.next = prev.next.next;
        return head;
    }

    public static boolean isHUIWEN(Node head){
        Node mid = midNode(head);
        Node reverseNode = reverseNode(mid.next);
        mid.next = reverseNode;
        boolean flag = true;
        while(reverseNode != null){
            if(head.data != reverseNode.data){
                flag = false;
                break;
            }
            head = head.next;
            reverseNode = reverseNode.next;
        }
        mid.next = reverseNode(mid.next);
        return flag;
    }


    public static void main(String[] args) {
        Node node1 = new Node(null, 1);
        Node node2 = new Node(null, 2);
//        Node node3 = new Node(null, 3);
        Node node4 = new Node(null, 2);
        Node node5 = new Node(null, 1);

        node1.next = node2;
        node2.next = node4;
//        node2.next = node3;
//        node3.next = node4;
        node4.next = node5;


        Node node6 = new Node(null, 1);
        Node node7 = new Node(null, 2);
        Node node8 = new Node(null, 3);

        node6.next = node7;
        node7.next = node8;
        System.out.println(midNode(node1));

//        node5.next = node1;

//        System.out.println(node1);
//        Node reverseNode = reverseNode(node1);
//        System.out.println(midNode(reverseNode));
//        System.out.println(hasCircle(node1));
//        System.out.println(merge(node1, node6));
//        System.out.println(node1);
//        Node node = delLastKNode(2, node1);
//        System.out.println(node);

        System.out.println(isHUIWEN(node1));
    }
}
