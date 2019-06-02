package sort;

import java.util.Arrays;

/**
 * 快速排序O(NlogN)
 * 不稳定  4   4   1   5
 * base
 *
 * @author yuh
 * @date 2019-06-01 07:23
 **/
public class QuickSort {

    public static void sort(int[] arr) {
        _sort(arr, 0, arr.length - 1);
    }

    private static void _sort(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }
        int index = partition(arr, l, r);
        _sort(arr, l, index - 1);
        _sort(arr, index + 1, r);
    }


    public static int topK(int[] arr, int k) {
        if (arr.length < k) {
            return -1;
        }
        int l = 0;
        int r = arr.length - 1;
        //局部排序后 索引在这个位置的
        int indexK = arr.length - k;
        while (l <= r) {
            int partition = partition(arr, l, r);
            if (partition < indexK) {
                l = partition + 1;
            } else if (partition > indexK) {
                r = partition - 1;
            } else {
                return partition;
            }
        }
        return -1;
    }


    private static int partition(int[] arr, int l, int r) {
        int bench = arr[l];
        int i = l + 1, j = r;
        while (i <= r && j > l) {
            while (i <= r && arr[i] <= bench) {
                i++;
            }
            while (j > l && arr[j] >= bench) {
                j--;
            }
            if (i > j) {
                break;
            }
            swap(arr, i, j);
            i++;
            j--;
        }
        swap(arr, j, l);
        return j;
    }


    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


    public static void main(String[] args) {
        int[] arr = {2, 1, 3, 4, 6, -1};
//        sort(arr);
//        System.out.println(Arrays.toString(arr));
        System.out.println(arr[topK(arr,1)]);
        System.out.println(arr[topK(arr,2)]);
        System.out.println(arr[topK(arr,3)]);
        System.out.println(arr[topK(arr,4)]);
    }
}
