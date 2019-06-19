package com.hy.learn.sort;

/**
 * 希尔排序
 * */
public class ShellSortDemo {

	public static void shellSort(int[] arr) {
		int increment = 1;
		int length = arr.length;
		while(increment<length/3) {
			increment = 3*increment+1;
		}
		while(increment>=1) {
			for(int i = 0;i<length;i++) {
				for(int j = i;j>=increment&&arr[j]<arr[j-increment];j--) {
					int temp = arr[j];
					arr[j] = arr[j-increment];
					arr[j-increment] = temp;
					
				}
			}
			increment = increment/3;
		}
	}
	
	public static void main(String[] args) {
		int[] arr = new int[] {5,9,10,3,80,6,18,53,1};
		shellSort(arr);
		for(int i = 0;i<arr.length;i++) {
			System.out.println(arr[i]);
		}
	}
}
