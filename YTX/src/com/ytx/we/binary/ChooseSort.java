package com.ytx.we.binary;

public class ChooseSort {
    public static void main(String[] args) {
        int[] arr = { 49, 38, 6, 97, 23, 22, 76, 1, 5, 8, 2, 0, -1, 22 };
        chooseSort(arr);
        System.out.println("排序后:");
        for (int i : arr) {
            System.out.print("【"+i+"】");
        }
    }
    private static void chooseSort(int[] arr){
        int currentIndex = 0;
        int length = arr.length-1;
        while(currentIndex <= length){

            int minIndex = currentIndex;
            //获取剩下数组的最小值对应的索引
            for(int i=currentIndex;i<=length;i++){
                if(arr[i] < arr[minIndex]){
                    minIndex = i;
                }
            }
            int tmp = arr[currentIndex];
            arr[currentIndex] = arr[minIndex];
            arr[minIndex] = tmp;
            currentIndex++;
        }


    }

}
