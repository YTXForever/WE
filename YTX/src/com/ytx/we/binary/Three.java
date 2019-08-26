package com.ytx.we.binary;

public class Three {


    static int[] array= new int[10];

    private static void backtrack(int[] arr,int k,int n)
    {
        if (k==n) {
            for(int i=0;i<=n;i++){
                System.out.print(arr[i]);
            }
            System.out.println();
            return;
        }else{
            for (int i = k; i <= n;i++) {
                swap(arr,i,k);
                backtrack(arr,k + 1, n);
                swap(arr,i,k);
            }
        }

    }
    public static void swap(int[] arr,int i,int k){
        int tmp = arr[i];
        arr[i] = arr[k];
        arr[k] = tmp;
    }
    public static void main(String[] args) {
        int[] arr={1,2,3};
        backtrack(arr,0,arr.length-1);
    }

}
