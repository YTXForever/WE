package sort;

import java.util.Arrays;

/**
 * 插入排序O(n2)
 * 稳定
 *
 * @author yuh
 * @date 2019-06-01 07:23
 **/
public class InsertSort {

    public static void sort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            int j = i;
            while(j > 0 && temp < arr[j-1]){
                arr[j] = arr[j-1];
                j--;
            }
            arr[j] = temp;
        }
    }

    public static void main(String[] args) {
        int[] arr = {6, 5, 4, 3, 2, 3};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
