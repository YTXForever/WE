package sort;

import java.util.Arrays;

/**
 * 冒泡排序 O(n2)
 * 稳定
 *
 * @author yuh
 * @date 2019-06-01 07:23
 **/
public class BubbleSort {

    public static void sort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < arr.length - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
    }

    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = {6, 5, 4, 3, 2, 3};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
