package com.daojia.testHY.althority;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**基数排序*/
public class RadixSortDemo {

	/**基数排序*/
	public static void radixSort(List<Long> arr,int index){
		List<List<Long>> bucket = new ArrayList<List<Long>>();
		for(int i = 0;i<10;i++){
			bucket.add(new ArrayList<>());
		}
		for(int i = 0;i<arr.size();i++){//12345
			int m =(int) ((arr.get(i)-(arr.get(i)/(BigDecimal.valueOf(10).pow(index).longValue())*(BigDecimal.valueOf(10).pow(index).longValue())))/(BigDecimal.valueOf(10).pow(index-1).longValue()));
			if(bucket.get(m)==null){
				bucket.add(m, new ArrayList<>());
			}else{
				bucket.get(m).add(arr.get(i));
			}
		}
		arr.clear();
		for(int i = 0;i<10;i++){
			arr.addAll(bucket.get(i));
		}
		if(index>=11)
			return;
		radixSort(arr,++index);
	}
	
	
	public static List<Long> buildArr(int length){
		List<Long> arr = new ArrayList<Long>(length);
		for(int i =0;i<length;i++){
			arr.add(getPhoneNo());
		}
		return arr;
	}
	public static Long getPhoneNo() {
		int firstNum = 1;
		int[] secondNumArray = {3,4,5,7,8};
		Random random = new Random();
		int secondNum = secondNumArray[random.nextInt(secondNumArray.length)];
		Long n = firstNum*BigDecimal.valueOf(10).pow(10).longValue()+secondNum*BigDecimal.valueOf(10).pow(9).longValue();
		for (int i = 9; i >0; i--) {
			Integer thirdNum = random.nextInt(10);
			
			n+=thirdNum*BigDecimal.valueOf(10).pow(i-1).longValue();
		}
		return n;
	}

	public static void main(String[] args) {
		List<Long> rr = buildArr(100000);
		long start = System.currentTimeMillis();
		radixSort(rr,1);
		long end = System.currentTimeMillis();
		
		for(int i = 0;i<rr.size();i++){
			System.out.println(rr.get(i));
		}
		System.out.println((end-start)+"ms");
	}
}
