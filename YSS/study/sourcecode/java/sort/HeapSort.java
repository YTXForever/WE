package sort;

import java.util.Arrays;

/**
 * 堆排序
 *
 * @author yuh
 * @date 2019-06-04 09:38
 **/
public class HeapSort {


    public static void sort(int[] arr) {
        for (int i = (arr.length / 2) - 1; i >= 0; i--) {
            //heapify
            shiftDown(arr, i, arr.length);
        }
        for (int i = arr.length - 1; i > 0; i--) {
            swap(arr, 0, i);
            shiftDown(arr, 0, i);
        }

    }


    private static void shiftDown(int[] arr, int index, int length) {
        while (index * 2 + 1 < length) {
            int k = index * 2 + 1;
            if (k + 1 < length && arr[k + 1] > arr[k]) {
                k++;
            }
            if (arr[k] <= arr[index]) {
                break;
            }
            swap(arr, index, k);
            index = k;
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static void main(String[] args) {
        int[] arr = {6, 5, 4, 10,-1,54,54,3634,6,346,34,6345,634,56,456};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
