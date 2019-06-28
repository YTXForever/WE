package frequentnum;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author yuh
 * @date 2019-06-29 07:11
 **/
public class FNum {



    public static int f1(int[] arr){
        HashMap<Integer, Integer> map = new HashMap<>();
        int curr = 0;
        int maxCount = 0;
        for (int i : arr) {
            Integer count = map.get(i);
            if(count == null){
                count = 1;
            }else{
                count += 1;
            }
            map.put(i,count);
            if(count >= arr.length/2){
                return i;
            }
            if(count > maxCount){
                maxCount = count;
                curr = i;
            }
        }
        return curr;
    }


    //====================== 1/2 超过1/2的元素可以选择摩尔投票法 适用于数据量非常大的情况
    public static int f2(int[] arr){
        int count = 0;
        int curr = 0;
        for (int i : arr) {
            if(count == 0){
                curr = i;
            }
            if(i == curr){
                count++;
            }else{
                count--;
            }
        }
        return curr;
    }

    //====================== 1/2 超过1/2的元素可以选择取中法  适用于数据量一般的情况
    public static int f3(int[] arr){
        Arrays.sort(arr);
        return arr[arr.length/2];
    }





    public static void main(String[] args) {
        int[] arr = {1,2,2,2,2,2,2,3,3,4,5,6,7,3,2,2,1};
        System.out.println(f3(arr));
    }
}