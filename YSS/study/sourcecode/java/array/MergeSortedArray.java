package array;

import java.util.Arrays;

/**
 * 合并有序数组
 *
 * @author yuh
 * @date 2019-05-27 08:04
 **/
public class MergeSortedArray {

    public static int[] merge(int[] arr1, int[] arr2) {
        if (arr1 == null || arr1.length == 0) {
            return arr2;
        }
        if (arr2 == null || arr2.length == 0) {
            return arr1;
        }
        int[] arr = new int[arr1.length + arr2.length];
        int i = 0, j = 0, k = 0;
        while (i < arr1.length || j < arr2.length) {
            if (i == arr1.length) {
                arr[k] = arr2[j];
                j++;
            } else if (j == arr2.length) {
                arr[k] = arr1[i];
                i++;
            } else if (arr1[i] < arr2[j]) {
                arr[k] = arr1[i];
                i++;
            } else {
                arr[k] = arr2[j];
                j++;
            }
            k++;
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] merge = merge(new int[]{1, 2, 3}, new int[]{1, 2, 3});
        System.out.println(Arrays.toString(merge));
    }
}
