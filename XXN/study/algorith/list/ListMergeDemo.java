package com.hy.learn.contruct.list;

import com.hy.learn.contruct.list.SingleListDemo.Node;

/**
 * 两个有序链表合并
 * */
public class ListMergeDemo {
	
	public static Node<Integer> merge(Node<Integer> head1,Node<Integer> head2){
		Node<Integer> head;
		if(head1==null||head2==null) {
			head = head1==null?head2:head1;
			return head;
		}
		head = head1.getNext().getData()<head2.getNext().getData()?head1:head2;
		Node<Integer> node1 = head1.getNext();
		Node<Integer> node2 = head2.getNext();
		Node<Integer> cur = head;
		while(node1!=null&&node2!=null) {
			if(node1.getData()<node2.getData()) {
				cur.setNext(node1);
				node1 = node1.getNext();
			}else {
				cur.setNext(node2);
				node2 = node2.getNext();
			}
			cur = cur.getNext();
		}
		cur.setNext(node1==null?node2:node1);
		return head;
	}

	public static void main(String[] args) {
		SingleListDemo<Integer> list1 = new SingleListDemo<>();
		SingleListDemo<Integer> list2 = new SingleListDemo<>();
		int[] arr = new int[]{1,3,5,7,9,11,13,15};
		for(int i = 0;i<arr.length;i++) {
			list1.addItem(arr[i]);
		}
		int[] arr2 = new int[]{2,4,6,8,10,12,14};
		for(int i = 0;i<arr2.length;i++) {
			list2.addItem(arr2[i]);
		}
		Node<Integer> temp= merge(list1.getHead(),list2.getHead());
		while(temp.getNext()!=null) {
			temp =temp.getNext();
			System.out.println(temp.getData());
		}
	}
}
