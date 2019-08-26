package com.ytx.we.binary;

import java.util.Random;
import java.util.Stack;

public class QuickSort {
    public static void main(String[] args) {
        int[] arr = getArr(150);
        long start = System.currentTimeMillis();
        quickSort(arr, 0, arr.length - 1);
        System.out.println("排序后:"+ "  耗时："+(System.currentTimeMillis()-start));
        for (int i : arr) {
            System.out.print("【"+i+"】");
        }
    }
    private static int[] getArr(int n){
        int[] arr = new int[n];
        Random r = new Random();
        for (int i = 0;i<n;i++){
            arr[i] = r.nextInt(1500)+1;
        }
        return arr;
    }
    private static void quickSort1(int[] arr,int s,int e){
        if(s>=e){
            return;
        }
        Stack<Integer> stack = new Stack<>();
        stack.push(e);
        stack.push(s);
        while (!stack.isEmpty()){
            int start = stack.pop();
            int end = stack.pop();
            int index = sortIndex(arr,start,end);
            if(index > start){
                stack.push(index-1);
                stack.push(start);
            }
            if(index<end){
                stack.push(end);
                stack.push(index+1);
            }

        }

    }
    private static void quickSort(int[] arr,int s,int e){
        if(s>=e){
            return;
        }
        int index = sortIndex(arr,s,e);
        quickSort(arr,0,index-1);
        quickSort(arr,index+1,e);
    }
    private static int sortIndex(int[] arr,int s,int e){
        int tmp = arr[s];
        while(s < e){
            while(s < e && arr[e] >= tmp){
                e--;
            }
            arr[s] = arr[e];
            while(s < e && arr[s] <= tmp){
                s++;
            }
            arr[e] = arr[s];
        }
        arr[s] = tmp;

        return s;

    }

}
