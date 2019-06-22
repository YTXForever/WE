package com.hy.learn.contruct.list;

import com.hy.learn.contruct.list.SingleListDemo.Node;

/**
 * 删除链表倒数第 n 个结点
 * 思路：
 * 1.设置两个指针，两个指针相差步长为n
 * 2.当步长快的指针的next指向null，那个步长慢的那个指针所在位置即为删除的点
 * */
public class DeleteNodeDemo {
	
	
	/**
	 * head-1-2-3-4-5-6-7-8-null   n=3
	 * head-1-2-3-4-5-6-8-null
	 * */
	public static void deleteItem(Node<Integer> node,int n) {
		Node<Integer> slow = node,fast = node;
		for(int i = 0;i<n;i++) {
			fast = fast.getNext();
		}
		while(fast.getNext()!=null) {
			fast = fast.getNext();
			slow = slow.getNext();
		}
		
		slow.setNext(slow.getNext().getNext());
	}
	public static void main(String[] args) {
		SingleListDemo<Integer> list = new SingleListDemo<>();
		int[] arr = new int[]{1,2,3,4,5,6,7,8,9};
		for(int i = 0;i<arr.length;i++) {
			list.addItem(arr[i]);
		}
		deleteItem(list.getHead(),5);
		Node<Integer> temp = list.getHead();
		while(temp.getNext()!=null) {	
			temp = temp.getNext();
			System.out.println(temp.getData());
		}
	}
}
