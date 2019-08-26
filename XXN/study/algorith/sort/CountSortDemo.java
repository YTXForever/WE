package com.daojia.testHY.althority;

public class CountSortDemo {
	
	public static int[] countingSort(int[] arr,int n){
		int max = 0;
		/**1.find max*/
		for(int i = 0;i<arr.length;i++){
			if(max<arr[i])
				max = arr[i];
		}
		int[] numArr = new int[max+1];
		/**2.计数放入桶中*/
		//2 0 2 3 0 1
		//0 1 2 3 4 5
		for(int i = 0;i<arr.length;i++){
			numArr[arr[i]]++;
		}
		
		//2 2 4 7 7 8
		//0 1 2 3 4 5
		for(int i = 1;i<=max;i++){
			numArr[i] = numArr[i-1]+numArr[i];
		}
		for(int i = 0;i<numArr.length;i++){
			System.out.println(numArr[i]);
		}
		/**3.初始化一个新的数组，length为n*/
		int[] newArr = new int[n];
		
		/**倒序进行    arr[i]=3,从numArr中找到 numArr[3]=7 说明<=3的有7个，即排序后第7个位置即为3   newArr[6] = 3*/
		for (int i = n - 1; i >= 0; --i) {
		   int index = numArr[arr[i]]-1;
		   newArr[index] = arr[i];
		   /**对应位置--，将数量减一，代表未排序的数量*/
		   numArr[arr[i]]--;
		}
		return newArr;
	}

	public static void main(String[] args) {
		int[] arr = new int[]{2,5,3,0,2,3,0,3};
		int[] sort = countingSort(arr,8);
		for(int i = 0;i<sort.length;i++){
			System.out.println(sort[i]);
		}
	}
}
