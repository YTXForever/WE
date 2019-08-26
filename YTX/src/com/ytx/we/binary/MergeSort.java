package com.ytx.we.binary;


public class MergeSort {
    public static void main(String[] args) {
        int[] arr = { 49, 38, 6, 97, 23, 22, 76, 1, 5, 8, 2, 0, -1, 22 };
        mergeSort(arr, 0, arr.length - 1);
        System.out.println("排序后:");
        for (int i : arr) {
            System.out.print("【"+i+"】");
        }
    }
    //[l,r]
    private static void mergeSort(int[] arr,int l,int r){
        if(l >= r){
            return;
        }
        int mid = (r+l)/2;
        //System.out.println("mid="+mid);
        mergeSort(arr,l,mid);
        mergeSort(arr,mid+1,r);
        if(arr[mid] > arr[mid+1]){
            merge(arr,l,mid,r);
        }
    }
    //[l,r]
    private static void merge(int[] arr,int l,int mid,int r){
        int[] arrTmp = new int[r-l+1];
        //拷贝一个数组
        for(int i=l;i<=r;i++){
            arrTmp[i-l] = arr[i];
        }

        int i = l,j = mid +1 ;

        for(int k = l;k<=r;k++){
            if(i >mid){
                arr[k] = arrTmp[j-l];
                j++;
            }else if(j > r){
                arr[k] = arrTmp[i-l];
                i++;
            } else if(arrTmp[i-l] <= arrTmp[j-l]){
                arr[k] = arrTmp[i-l];
                i++;
            }else{
                arr[k] = arrTmp[j-l];
                j++;
            }

        }

    }

}
