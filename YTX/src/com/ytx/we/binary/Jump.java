package com.ytx.we.binary;

public class Jump {
    public static void main(String[] args) {
        //int[] arr = { 3, 9, 7, 8, 4, 2, 9, 1, 5, 8, 1, 2 };
        //int[] arr = { 1,2,3,4,5 };
        int[] arr = {1, 2, 4, 1, 5};
        jump(arr,0,0);
        System.out.println("--------");
        jump(arr);

    }
    //非递归
    private static void jump(int[] arr){
        if(arr[0] > arr.length-1){
            return;
        }
        int i =0;
        int nextJumpIndex = 0;
        int lastStayIndex = 0;
        while (i < arr.length){
            int maxStep = arr[lastStayIndex];
            if(i+maxStep >arr.length-1){
                return;
            }
            int j;
            //以下循环结束后，j是遍历的最后位置
            //nextJumpIndex 是需要跳的位置
            for(j =i+1;j<= arr[lastStayIndex]+lastStayIndex && j<arr.length;j++){
                if(maxStep <= j-i+arr[j]){
                    maxStep = j-i+arr[j];
                    nextJumpIndex = j;
                }
            }
            i=j;
            lastStayIndex = nextJumpIndex;
            System.out.println("index="+nextJumpIndex+",value="+arr[nextJumpIndex]);
        }

    }

    /**
     *
     * @param arr
     * @param s 查找下一次跳的位置时，起始遍历位置
     * @param lastJumpIndex 上一次跳的位置 s有可能不等于lastJumpIndex
     */
    private static void jump(int[] arr,int s,int lastJumpIndex){
        int len = arr.length;
        if(lastJumpIndex+arr[lastJumpIndex] > len-1){
            return;
        }
        int maxStep = arr[lastJumpIndex],nextJumpIndex = s+1;
        for(int i=s;i<=lastJumpIndex+arr[lastJumpIndex]
                && i< len;i++){
            //[s,lastJumpIndex+arr[lastJumpIndex]]在此区间找可跳最大步数
            if(maxStep < i-lastJumpIndex+arr[i]){
                maxStep = i-lastJumpIndex+arr[i];
                nextJumpIndex = i;
            }
            s++;
        }
        System.out.println("index="+nextJumpIndex+",value="+arr[nextJumpIndex]);
        jump(arr,s,nextJumpIndex);
    }

}
