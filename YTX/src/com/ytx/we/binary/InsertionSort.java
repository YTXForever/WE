package com.ytx.we.binary;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class InsertionSort {
    public static void main(String[] args) {
        int[] arr = { 49, 38, 6, 97, 23, 22, 76, 1, 5, 8, 2, 0, -1, 22 };
        insertionSort(arr);
        System.out.println("排序后:");
        for (int i : arr) {
            System.out.print("【"+i+"】");
        }
        Executors.newFixedThreadPool(5).submit(new Callable<Object>() {
            public Object call(){
                return null;
            }
        });
    }
    private static void insertionSort(int[] arr){
        int length = arr.length;
        for(int i=1;i<length;i++){
            for(int j=i;j>0;j--){
                if(arr[j-1] > arr[j]){
                    //swap
                    int tmp = arr[j-1];
                    arr[j-1] = arr[j];
                    arr[j] = tmp;
                }else {
                    break;
                }
            }
        }
    }


}
