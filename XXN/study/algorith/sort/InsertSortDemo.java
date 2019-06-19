package com.hy.learn.sort;

/**插入排序*/
public class InsertSortDemo {

	public static void insertSort(int[] arr) {
		for(int i = 1;i<arr.length;i++) {
			for(int j = 0;j<i;j++) {
				if(arr[j]>arr[i]) {
					int temp = arr[j];
					arr[j] = arr[i];
					arr[i] = temp;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		int[] arr = new int[] {5,9,10,3,80,6,18,53,1};
		insertSort(arr);
		for(int i = 0;i<arr.length;i++) {
			System.out.println(arr[i]);
		}
	}
	
}
