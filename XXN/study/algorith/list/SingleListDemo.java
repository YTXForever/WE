package com.hy.learn.contruct.list;

/**
 * 单向链表  增删改
 * */
public class SingleListDemo<T> {
	private Node<T> head;
	private Node<T> tail;
	private int size;
	static class Node<T>{
		T data;
		private Node<T> next;
		public Node (T data2){
			this.data = data2;
		}
		public Node<T> getNext() {
			return next;
		}
		public void setNext(Node<T> next) {
			this.next = next;
		}
		public T getData() {
			return data;
		}
		public void setData(T data) {
			this.data = data;
		}
		
	}  
	
	public SingleListDemo() {
		head = new Node<>(null);
		
	}
	
	public void addItem(T data) {
		Node<T> node = new Node<>(data);
		Node<T> temp = tail;
		size++;
		if(tail ==null) {
			/**是个空节点*/
			tail = node;
			head.next = tail;
			return;
		}
		temp.next = node;
		tail = node;
	}
	/**
	 * head-1-2-3-tail temp=1
	 * head-1
	 * */
	public void remove(T data) {
		Node<T>temp = head.next;
		if(temp.data==data) {
			head.next = temp.next;
			return;
		}
		while(temp!=null&&temp.next!=null&&!temp.next.data.equals(data)) {
			temp = temp.next;
		}
		if(temp==null) {
			System.out.println("未找到该节点");
			return;
		}
		size--;
		Node<T> mm = temp.next.next;
		if(mm==null) {
			/**要删除的节点是尾节点*/
			tail = temp;
			temp.next = null;
		}else {
			temp.next = mm;
		}
	}
	
	public void insertNode(T data,Node<T> insertPos) {
		Node<T> node = new Node<>(data);
		Node<T> temp = head.next;
		/**尾节点特殊处理*/
		if(tail.data.equals(insertPos.data)) {
			Node<T> temp1 = tail;
			temp1.next = node;
			tail = node;
			return;
		}
		while(temp.next!=null&&!temp.data.equals(insertPos.data)) {
			temp = temp.next;
		}
		if(temp.next==null) {
			System.out.println("未找到插入点");
			return;
		}
		node.next=temp.next;
		temp.next = node;
	}
	
	public Node<T> getHead() {
		return head;
	}

	public void setHead(Node<T> head) {
		this.head = head;
	}

	public Node<T> getTail() {
		return tail;
	}

	public void setTail(Node<T> tail) {
		this.tail = tail;
	}

	public static void main(String[] args) {
		SingleListDemo<Integer> list = new SingleListDemo<>();
		int[] arr = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		for(int i = 0;i<arr.length;i++) {
			list.addItem(arr[i]);
		}
		Node<Integer> temp = list.head;
		list.insertNode(90,new Node<Integer>(19));
		while(temp.next!=null) {	
			temp = temp.next;
			System.out.println(temp.data);
		}
	}
}
