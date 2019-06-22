package com.hy.learn.contruct.list;

import com.hy.learn.contruct.list.SingleListDemo.Node;

/**
 * 链表检测环
 * 两个指针，步长不一样。如果没有环，那么步长长的会提前遍历完；
 * 如果由环，那么两个指针终会相遇
 * 1----2----4----5-----6
 *           |          |
 *           |          |
 *           8----7------
 * */
public class ListCircleTest {

	public static boolean hasLoop(Node<Integer> head) {
		Node<Integer> p = head;
		Node<Integer> q = head.getNext();
		while(q!=null&&q.getNext()!=null) {
			p = p.getNext();
			q = q.getNext().getNext();
			if(q==null) {
				return false;
			}
			if(q.getData().equals(p.getData())) {
				return true;
			}
		}
		return false;
	}
	
	public static void buildCycleList(SingleListDemo<Integer> list) {
		Node<Integer> node = list.getHead().getNext();
		while(node!=null) {
			if(node.getData()==4) {
				break;
			}
			node = node.getNext();
		}
		Node<Integer> tail = list.getTail();
		tail.setNext(node);
		list.setTail(node);
	}
	
	public static void main(String[] args) {
		SingleListDemo<Integer> list = new SingleListDemo<>();
		int[] arr = new int[]{1,2,3,4,5,6,7,8,9};
		for(int i = 0;i<arr.length;i++) {
			list.addItem(arr[i]);
		}
		buildCycleList(list);
		boolean result = hasLoop(list.getHead());
		System.out.println("----有环------"+result);
	}
}
