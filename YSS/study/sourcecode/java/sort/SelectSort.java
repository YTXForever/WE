package sort;

import java.util.Arrays;

/**
 * 选择排序 O(n2)
 * 不稳定 100 100 2 1
 * @author yuh
 * @date 2019-06-01 07:23
 **/
public class SelectSort {

    public static void sort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[min] > arr[j]) {
                    min = j;
                }
            }
            if (min != i) {
                swap(arr, i, min);
            }
        }
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = {6,5,4,3,2,3};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
