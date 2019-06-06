package fenzhi;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 归并排序O(NlogN)
 * 不稳定  4   4   1   5
 * base
 *
 * @author yuh
 * @date 2019-06-01 07:23
 **/
public class MergeSort {

    public static int sort(int[] arr) {
        int[] cp = new int[arr.length];
        AtomicInteger counter = new AtomicInteger();
        _sort(arr, 0, arr.length - 1, cp, counter);
        return counter.get();
    }

    private static void _sort(int[] arr, int l, int r, int[] cp, AtomicInteger counter) {
        if (l >= r) {
            return;
        }
        int index = (l + r) / 2;
        _sort(arr, l, index, cp, counter);
        _sort(arr, index + 1, r, cp, counter);
        merge(arr, l, r, index, cp, counter);
    }

    private static void merge(int[] arr, int l, int r, int index, int[] cp, AtomicInteger counter) {
        System.arraycopy(arr, l, cp, l, r - l + 1);
        int i = l, j = index + 1, k = l;
        while (i <= index || j <= r) {
            if (i > index) {
                arr[k] = cp[j];
                j++;
            } else if (j > r) {
                arr[k] = cp[i];
                i++;
            } else if (cp[i] <= cp[j]) {
                arr[k] = cp[i];
                i++;
            } else {
                arr[k] = cp[j];
                j++;
                counter.addAndGet(index - i + 1);
            }
            k++;
        }
    }

    public static void main(String[] args) {
        int[] arr = {3, 2, 1};
        int sort = sort(arr);
        System.out.println(sort);
        System.out.println(Arrays.toString(arr));
    }


}
