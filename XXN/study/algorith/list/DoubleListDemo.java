package com.hy.learn.contruct.list;

import com.taobao.pac.sdk.cp.dataobject.request.CUSTOMS_BILL_CALLBACK.Head;

public class DoubleListDemo<T> {
	private int size;
	private Node<T> head;
	private Node<T> tail;

	/**
	 * 增加
	 * */
	public void addItem(T data) {
		Node<T> node = new Node<>(data);
		if(tail==null) {
			head = new Node<>();
			head.next = node;
			node.prev = head;
			tail = node;
			return;
		}
		Node<T> temp = tail;
		temp.next = node;
		node.prev = temp;
		tail = node;
	}
	
	/**删除
	 * head-1-2-3-tail*/
	public void remove(T data) {
		Node<T> temp = head.next;
		if(head.next==null) {
			return;
		}
		if(head.next.getData().equals(data)) {
			head.next = head.next.next;
			head.next.prev = head;
			return;
		}
		while(temp!=null&&!temp.next.getData().equals(data)) {
			temp = temp.next;
		}
		if(tail.getData().equals(data)) {
			temp.next = null;
			tail = temp;
		}else {
			temp.next = temp.next.next;
			temp.next.prev = temp;
		}
	}
	
	/**
	 * 
	 * head-1-tail
	 * head-1-2-3-tail
	 * */
	public void insertNode(T data,T insertPos) {
		if(head==null||head.next==null) {
			return;
		}
		Node<T> insertNode = new Node<T>(data);
		Node<T> temp = tail;
		if(tail.data.equals(insertPos)) {
			temp.next = insertNode;
			insertNode.prev =temp;
			tail = insertNode;
			return;
		}
		Node<T> dd = head.next;
		while(dd!=null&&!dd.data.equals(insertPos)) {
			dd = dd.next;
		}
		if(dd.next==null) {
			System.out.println("未找到");
			return;
		}
		
		dd.next.prev = insertNode;
		insertNode.next = dd.next;
		dd.next = insertNode;
		insertNode.prev = dd;
	}
	
	static class Node<T>{
		private T data;
		private Node<T> next;
		private Node<T> prev;
		public Node() {}
		public Node(T data) {
			this.data = data;
		}
		public Node(T data, Node<T> next, Node<T> prev) {
			super();
			this.data = data;
			this.next = next;
			this.prev = prev;
		}
		public T getData() {
			return data;
		}
		public void setData(T data) {
			this.data = data;
		}
		public Node<T> getNext() {
			return next;
		}
		public void setNext(Node<T> next) {
			this.next = next;
		}
		public Node<T> getPrev() {
			return prev;
		}
		public void setPrev(Node<T> prev) {
			this.prev = prev;
		}
		
	}
	
	public static void main(String[] args) {
		DoubleListDemo<Integer> list = new DoubleListDemo<>();
		int[] arr = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		for(int i = 0;i<arr.length;i++) {
			list.addItem(arr[i]);
		}
		list.insertNode(90, 1);
		Node<Integer> temp = list.head.next;
		/**正向遍历*/
		while(temp!=null) {
			System.out.println(temp.data);
			temp = temp.next;
		}
		/**逆向遍历*/
		Node<Integer> reserve = list.tail;
		while(reserve.prev!=null) {
			System.out.println(reserve.data);
			reserve = reserve.prev;
		}
	}

}
