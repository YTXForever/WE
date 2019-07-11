package com.daojia.testHY.althority;

public class MergeSortDemo {

	public static void merge(int[] arr,int low,int middle,int high){
		int[] temp = new int[high-low+1];
		int i = low;
		int j = middle+1;
		int k = 0;
		while(i<=middle&&j<=high){
			if(arr[i]<arr[j]){
				temp[k] = arr[i];
				i++;
			}else{
				temp[k] =arr[j];
				j++;
			}	
			k++;
		}
		while(i<=middle){
			temp[k]=arr[i];
			k++;
			i++;
		}
		while(j<=high){
			temp[k]=arr[j];
			k++;
			j++;
		}
		for(int x=0;x<temp.length;x++){
			arr[x+low] = temp[x];
        }
	}
	
	public static void mergeSort(int[] arr,int low,int high){
		if(low>=high){
			return;
		}
		int middle = (low+high)/2;
		mergeSort(arr,low,middle);
		mergeSort(arr,middle+1,high);
		merge(arr,low,middle,high);
	}
	
	public static void main(String[] args) {
		int[] arr = new int[] {5,9,10,3,80,6,18,53,1};
		mergeSort(arr,0,8);
		for(int i = 0;i<arr.length;i++) {
			System.out.println(arr[i]);
		}
	}
}
