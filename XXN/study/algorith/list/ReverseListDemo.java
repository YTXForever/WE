package com.hy.learn.contruct.list;

import com.hy.learn.contruct.list.SingleListDemo.Node;

/**
 * 链表反转 head-1-2-3-4-5-6-7-tail tail-7-6-5-4-3-2-1-head
 * 
 */
public class ReverseListDemo {
	public static Node<Integer> reverse(Node<Integer> head) {
		// 单链表为空或只有一个节点，直接返回原单链表
		if (head == null || head.getNext() == null) {
			return head;
		}
		// 前一个节点指针
		Node<Integer> preNode = null;
		// 当前节点指针
		Node<Integer> curNode = head;
		// 下一个节点指针
		Node<Integer> nextNode = null;

		while (curNode != null) {
			nextNode = curNode.getNext();// nextNode 指向下一个节点
			curNode.setNext(preNode);// 将当前节点next域指向前一个节点
			preNode = curNode;// preNode 指针向后移动
			curNode = nextNode;// curNode指针向后移动
		}

		return preNode;

	}

	public static void main(String[] args) {
		SingleListDemo<Integer> list = new SingleListDemo<>();
		int[] arr = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
		for (int i = 0; i < arr.length; i++) {
			list.addItem(arr[i]);
		}
		Node<Integer> temp = reverse(list.getHead());
		while (temp.getNext() != null) {
			System.out.println(temp.data);
			temp = temp.getNext();

		}
	}
}
