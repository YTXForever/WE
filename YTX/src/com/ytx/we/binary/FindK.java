package com.ytx.we.binary;



public class FindK {
    public static void main(String[] args) {
        /*int[] arr = { 49, 38, 6, 97, 23, 22, 76, 1, 5, 8, 2, 0, -1, 22 };
        int kvalue = findk(arr,0,arr.length-1,5);
        System.out.println("第5大大数据是:"+kvalue);
        for (int i=0;i<arr.length-1;i++) {
            System.out.print("【"+arr[i]+"】");
        }*/
        long start = System.currentTimeMillis();
        System.out.println(fibonacci(10000)+ "  耗时："+(System.currentTimeMillis()-start));

    }
    private static long fibonacci(int n){
        //f(n) = f(n-1)+f(n-2)
        if(n == 0) return 0;
        if(n == 1) return 1;
        return fibonacci(n-1)+fibonacci(n-2);

        /*long[] arr = new long[n+1];
        arr[0] =0;
        arr[1] =1;
        for(int i=2;i<=n;i++){
            arr[i] = arr[i-1]+arr[i-2];
        }
        return arr[n];*/
    }
    private static int findk(int[] arr,int s,int e,int k){
        if(s >= e){
            return arr[s];
        }
        int index = partition(arr,s,e);
        //【s，index】区间包含的数据个数 = index+1
        if(index+1 == k){
            return arr[index];
        }
        if(index+1 > k){
            //第k大大数据在【s，index)区间
            return findk(arr,s,index-1,k);
        }else {
            //第k大大数据在(index，e】区间
            return findk(arr,index+1,e,k);
        }

    }

    private static int partition(int[] arr,int s,int e){
        if(s >= e){
            return s;
        }
        int tmp = arr[s];
        while (s <e){
            while (e>s && arr[e] < tmp){
                e--;
            }
            arr[s] = arr[e];
            while (e>s && arr[s] >= tmp){
                s++;
            }
            arr[e]= arr[s];
        }
        arr[s] = tmp;

        return s;
    }


}
