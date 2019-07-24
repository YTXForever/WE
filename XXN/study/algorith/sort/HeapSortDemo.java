package com.hy.learn.sort;

/**堆排序*/
public class HeapSortDemo {

	static class Node{
		private int data;
		private Node lchild;
		private Node rchild;
		public Node(int data, Node lchild, Node rchild) {
			super();
			this.data = data;
			this.lchild = lchild;
			this.rchild = rchild;
		}
		public int getData() {
			return data;
		}
		public void setData(int data) {
			this.data = data;
		}
		public Node getLchild() {
			return lchild;
		}
		public void setLchild(Node lchild) {
			this.lchild = lchild;
		}
		public Node getRchild() {
			return rchild;
		}
		public void setRchild(Node rchild) {
			this.rchild = rchild;
		}
		
	}

	public static void heapfy(int[] arr,int n, int index) {
		while(true) {
			int maxPos = index;
			if(2*index+1<n&&arr[maxPos]<arr[2*index+1])
				maxPos = 2*index+1;
			if(2*index+2<n&&arr[maxPos]<arr[2*index+2])
				maxPos = 2*index+2;
			if(maxPos==index)
				break;
			int temp = arr[maxPos];
			arr[maxPos] = arr[index];
			arr[index] = temp;
			index = maxPos;
		}
	}
	
	public static void buildHeapTree(int[] arr,int n) {
		for(int i = n/2-1;i>=0;i--) {
			heapfy(arr,n,i);

		}
	}
	
	public static void sort(int[] arr) {
		for(int i = arr.length-1;i>=0;i--) {
			int temp = arr[0];
			arr[0] = arr[i];
			arr[i] = temp;
			buildHeapTree(arr,i);
		}
	}
	
	/**
	  * 查询topK个元素
	 * */
	public static void findTopK(int K,int[] arr) {
		
	}
	
	public static void main(String[] args) {
		int arr[] = {7,5,19,8,4,1,20,13,16};
		buildHeapTree(arr,arr.length);
		sort(arr);
		for(int m = 0;m<arr.length;m++) {
			System.out.print(arr[m]+",");
		}
		System.out.println();
	}
}
