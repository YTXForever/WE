package com.hy.learn.sort;

/**
 * 给定数组
 * 查询topK个元素
 * */
public class TopKDemo {
	public static void heapfy(int[] arr,int n, int index) {
		while(true) {
			int maxPos = index;
			if(2*index+1<n&&arr[maxPos]>arr[2*index+1])
				maxPos = 2*index+1;
			if(2*index+2<n&&arr[maxPos]>arr[2*index+2])
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
	
	/**
	  * 查询topK个元素
	  * 
	  * 思路：采用堆排序，小顶堆(arr[0]是最小的；每次与最小的相比；最终能得到topK)
	 * */
	public static int[] findTopK(int K,int[] arr) {
		buildHeapTree(arr,K);

		int[] temp = new int[K];
		for(int i = 0;i<K;i++) {
			temp[i] = arr[i];
		}
		for(int i =K;i<arr.length;i++) {
			if(temp[0]<arr[i]) {
				temp[0] = arr[i];
				buildHeapTree(temp,K);
			}
		}
		return temp;
	}
	
	public static void main(String[] args) {
		int arr[] = {7,5,19,8,4,1,20,13,16};
		int[] b = findTopK(3,arr);
		for(int m = 0;m<b.length;m++) {
			System.out.print(b[m]+",");
		}
	}
}
