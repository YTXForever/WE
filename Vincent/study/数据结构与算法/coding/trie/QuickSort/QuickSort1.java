package QuickSort;

import tools.Utils;

import java.util.Random;

public class QuickSort1 {

    public static void sort(int[] arr, int start, int end) {

        if (start >= end) {
            return;
        }

        int mid = partition(arr, start, end);

        sort(arr, start, mid - 1);
        sort(arr, mid + 1, end);
    }

    private static int partition(int[] arr, int start, int end) {
        int v = arr[start];
        int j = start;
        int i = start + 1;
        for (; i <= end; i++) {
            if (arr[i] < v) {
                int temp = arr[i];
                arr[i] = arr[j + 1];
                arr[j + 1] = temp;
                j++;
            }
        }
        arr[start] = arr[j];
        arr[j] = v;
        return j;
    }

    public static void sort1(int[] arr, int start, int end) {

        if (start >= end) {
            return;
        }

        int mid = partition1(arr, start, end);

        sort1(arr, start, mid - 1);
        sort1(arr, mid + 1, end);
    }

    private static int partition1(int[] arr, int start, int end) {
        int vIndex = new Random().nextInt(end - start) + start;
        swap(arr, vIndex, start);

        int l = start;
        int v = arr[l];

        int j = start;
        int i = start + 1;
        for (; i <= end; i++) {
            if (arr[i] < v) {
                swap(arr, i, j + 1);
                j++;
            }
        }
        swap(arr, l, j);
        return j;
    }

    public static void swap(int[] arr, int aIndex, int bIndex) {
        int temp = arr[aIndex];
        arr[aIndex] = arr[bIndex];
        arr[bIndex] = temp;
    }

    public static void check(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int min = arr[i];
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < min) {
                    System.out.println("校验不通过");
                    return;
                }
            }
        }
        System.out.println("校验通过");
    }

    public static void main(String[] args) {
        int arrLength = 10000;
        int swapCount = 10;
        int[] arr = Utils.getNearSortArr(arrLength, swapCount);
        int[] arr1 = Utils.copyArr(arr);

        long time0 = System.currentTimeMillis();
        sort(arr, 0, arrLength - 1);
        long time1 = System.currentTimeMillis();
        sort1(arr1, 0, arrLength - 1);
        long time2 = System.currentTimeMillis();

        System.out.println("sort耗时：" + (time1 - time0) + "ms");
        System.out.println("sort1耗时：" + (time2 - time1) + "ms");

        check(arr);
        check(arr1);
    }

}
