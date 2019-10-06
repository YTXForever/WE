package com.ytx.we.binary;

import java.util.Stack;

public class FindMax {
    public static int[] findMax(int[] arr){
        int[] res = new int[arr.length];
        Stack<Integer> stack = new Stack<>();
        int i = 0;
        while(i<arr.length){
            if (stack.empty() || arr[stack.peek()] > arr[i]){
                stack.push(i++);
            }else{
                int index = stack.pop();
                res[index] = arr[i];
            }
        }
        while (!stack.empty()){
            int index = stack.pop();
            res[index] = Integer.MAX_VALUE;
        }
        return res;
    }

    public static void main(String[] args) {
        //int[] arr = new int[]{20,3,8,23,24,9,11,50};
       /* int[] arr = new int[]{20,3,8,23,24,9,11,50,5};
        int[] res = findMax(arr);
        for(int i=0;i<res.length;i++){
            System.out.print("【"+res[i]+"】");
        }*/
        System.out.println((int)'Z');
        System.out.println();
       int c = 'A'+0;
       for(int i =0;i<100;i+=2){
           System.out.print(i);
           System.out.print(i+1);
           System.out.print((char)c);
           c++;
           if(c>'Z'+0){
               c='A'+0;
           }

       }

    }
}
