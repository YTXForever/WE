package QuickSort;

import tools.Utils;

import java.util.Random;

public class QuickSort2 {

    public static void sort(int[] arr, int start, int end) {
        if (start >= end) {
            return;
        }

        int mid = partition(arr, start, end);

        sort(arr, start, mid - 1);
        sort(arr, mid + 1, end);
    }

    private static int partition(int[] arr, int l, int r) {
        int vIndex = new Random().nextInt(r - l) + l;
        Utils.swap(arr, vIndex, l);

        int v = arr[l];

        int i = l + 1;
        int j = r;

        while (i < j) {
            if (arr[i] < v) {
                i++;
            }
            if (arr[j] > v) {
                j--;
            }
            if (i < j && arr[i] >= v && arr[j] <= v) {
                Utils.swap(arr, i, j);
                i++;
                j--;
            }
        }

        if (i == j) {
            if (arr[i] < v) {
                i++;
            } else {
                j--;
            }
        }

        Utils.swap(arr, l, j);

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

    private static int partition1(int[] arr, int l, int r) {
        int vIndex = new Random().nextInt(r - l) + l;
        Utils.swap(arr, vIndex, l);

        int v = arr[l];

        int i = l + 1;
        int j = r;

        while (true) {
            while (i <= r && arr[i] < v) i++;
            while (j > l && arr[j] > v) j--;

            if (i > j) break;

            if (arr[i] >= v && arr[j] <= v) {
                Utils.swap(arr, i, j);
                i++;
                j--;
            }
        }

        Utils.swap(arr, l, j);

        return j;
    }

    public static void main(String[] args) {
        int arrLength = 1000000;
        int[] arr = Utils.getNewArr(arrLength);
        int[] arr1 = Utils.copyArr(arr);

        long time0 = System.currentTimeMillis();
        sort(arr, 0, arrLength - 1);
        long time1 = System.currentTimeMillis();
        sort1(arr1, 0, arrLength - 1);
        long time2 = System.currentTimeMillis();


        System.out.println("sort耗时：" + (time1 - time0) + "ms");
        System.out.println("sort1耗时：" + (time2 - time1) + "ms");

        Utils.checkAsc(arr, "sort");
        Utils.checkAsc(arr1, "sort");
    }

}
