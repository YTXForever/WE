package com.hy.learn.sort;

public class SelectSortDemo {

	public static void selectSort(int[] arr) {
		if(arr.length==0) {
			return;
		}
		for(int i = 0;i<arr.length;i++) {
			for(int j = i;j<arr.length;j++) {
				if(arr[i]>arr[j]) {
					int temp = arr[j];
					arr[j] = arr[i];
					arr[i] = temp;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		int[] arr = new int[] {5,9,10,3,80,6,18,53,1};
		selectSort(arr);
		for(int i = 0;i<arr.length;i++) {
			System.out.println(arr[i]);
		}
	}
}
