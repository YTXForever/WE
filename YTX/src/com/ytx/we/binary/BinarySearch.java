package com.ytx.we.binary;

public class BinarySearch {
    public static void main(String[] args) {
        int[] arr = { -1, 5, 40, 50, 55, 55,55,55, 52,10,5};
        //int[] arr = { -1, 5, 40, 50, 55, 52,10,5};
        int index = binarySearchMax(arr,0,arr.length-1);
        System.out.println(index);
        System.out.println(index == -1?"null":arr[index]);

    }

    private static int binarySearchMax(int[] arr,int s,int e){
        if(arr.length < 1){
            return -1;
        }
        if(s >= e){
            return s;
        }
        int m = (s+e)/2;
        int rightValue = arr[m +1];
        int midValue = arr[m];
        if(midValue>rightValue){
            //最大值在左区间
            return binarySearchMax(arr,s,m);
        }else if(midValue<rightValue){
            //最大值在右区间
            return binarySearchMax(arr,m+1,e);
        }else{
            int leftValue = m <= s?arr[s]:arr[m-1];
            //最大值可能在左区间也可能在右区间
            if(midValue >leftValue){
                //最大值在右区间
                return binarySearchMax(arr,m+1,e);
            }else if(midValue <leftValue){
                //在左区间
                return binarySearchMax(arr,s,m);
            }else{
                //leftValue,midValue,rightValue 都相等
                int leftMaxIndex = binarySearchMax(arr,s,m);
                int rightMaxIndex = binarySearchMax(arr,m+1,e);
                if(arr[leftMaxIndex] >= arr[rightMaxIndex]){
                    return leftMaxIndex;
                }else {
                    return rightMaxIndex;
                }
            }
        }

    }


    private static int binarySearch(int[] arr,int start,int end,int key){
        if(start > end){
            return -1;
        }
        int mid = (end+start)/2;// start/2-end/2+end
        if(arr[mid] == key){
            return mid;
        }else if(arr[mid] < key){
            return binarySearch(arr,mid+1,end,key);
        }else {
            return binarySearch(arr,start,mid-1,key);
        }
    }
    private static int binarySearch2(int[] arr,int start,int end,int key){
        if(start > end){
            return -1;
        }
        // start/2-end/2+end
        while (start < end){
            int mid = (end+start)/2;
            if(arr[mid] == key){
                return mid;
            }else if(arr[mid] < key){
                start = mid+1;
            }else {
                end = mid -1;
            }
        }
        return  -1;
    }
}
