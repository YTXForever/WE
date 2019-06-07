package QuickSort;

import tools.Utils;

import java.util.Random;

public class QuickSort3 {

    public static void sort(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }

        int vIndex = new Random().nextInt(r - l) + l;
        Utils.swap(arr, vIndex, l);

        int v = arr[l];

        int lt = l;
        int gt = r + 1;
        int i = l + 1;

        for (; i < gt; i++) {
            if (arr[i] == v) {
                continue;
            } else if (arr[i] < v) {
                Utils.swap(arr, lt + 1, i);
                lt++;
            } else if (arr[i] > v) {
                Utils.swap(arr, gt - 1, i);
                gt--;
                i--;
            }
        }

        Utils.swap(arr, l, lt);


        sort(arr, l, lt - 1);
        sort(arr, gt, r);
    }

    public static void sort1(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }

        int vIndex = new Random().nextInt(r - l) + l;
        Utils.swap(arr, vIndex, l);

        int v = arr[l];

        int lt = l;
        int gt = r + 1;
        int i = l + 1;

        while (i < gt) {
            if (arr[i] == v) {
                i++;
            } else if (arr[i] < v) {
                Utils.swap(arr, lt + 1, i);
                lt++;
                i++;
            } else if (arr[i] > v) {
                Utils.swap(arr, gt - 1, i);
                gt--;
            }
        }

        Utils.swap(arr, l, lt);


        sort1(arr, l, lt - 1);
        sort1(arr, gt, r);
    }

    public static void main(String[] args) {
        int arrLength = 10000000;
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
        Utils.checkAsc(arr1, "sort1");
    }

}
