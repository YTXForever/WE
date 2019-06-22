package com.hy.learn.contruct.list;

import com.hy.learn.contruct.list.SingleListDemo.Node;

/**
 * 求链表中间节点
 * 1.两个指针，slow一倍速度；fast二倍速度；当fast->next = null;slow->Node(middle)
 * */
public class MiddleNodeDemo {

	public static Node<Integer> getMiddleNode(Node<Integer> head){
		Node<Integer> slow = head,fast=head;
		while(fast!=null&&fast.getNext()!=null) {
			slow = slow.getNext();
			fast = fast.getNext().getNext();
		}
		return slow;
	}
	
	public static void main(String[] args) {
		SingleListDemo<Integer> list = new SingleListDemo<>();
		int[] arr = new int[]{1,2,3,4,5,6,7,8,9,10,11};
		for(int i = 0;i<arr.length;i++) {
			list.addItem(arr[i]);
		}
		
		Node<Integer> temp = getMiddleNode(list.getHead());
		System.out.println(temp.getData());

	}
}
