package MergeSort;

import java.util.Random;

public class MergeSort1 {

    public static void sort(int[] arr, int start, int end) {
        if (start >= end) {
            return;
        }

        int mid = (start + end) / 2;
        sort(arr, start, mid);
        sort(arr, mid + 1, end);

        int left = start;
        int right = mid + 1;
        int[] copyArr = new int[arr.length];
        System.arraycopy(arr, 0, copyArr, 0, arr.length);
        for (int dest = start; dest <= end; dest++) {
            if (left > mid) {
                arr[dest] = copyArr[right];
                right++;
            } else if (right > end) {
                arr[dest] = copyArr[left];
                left++;
            } else if (copyArr[left] <= copyArr[right]) {
                arr[dest] = copyArr[left];
                left++;
            } else {
                arr[dest] = copyArr[right];
                right++;
            }
        }
    }

    public static void sort1(int[] arr, int start, int end) {
        if (start >= end) {
            return;
        }

        int mid = (start + end) / 2;
        sort1(arr, start, mid);
        sort1(arr, mid + 1, end);

        int left = start - start;
        int right = mid + 1 - start;
        int mid0 = mid - start;
        int[] copyArr = new int[end - start + 1];
        System.arraycopy(arr, start, copyArr, 0, end - start + 1);
        for (int dest = start; dest <= end; dest++) {
            if (left > mid0) {
                arr[dest] = copyArr[right];
                right++;
            } else if (right >= copyArr.length) {
                arr[dest] = copyArr[left];
                left++;
            } else if (copyArr[left] <= copyArr[right]) {
                arr[dest] = copyArr[left];
                left++;
            } else {
                arr[dest] = copyArr[right];
                right++;
            }
        }
    }

    public static void sort2(int[] arr, int start, int end) {
        if (start >= end) {
            return;
        }

        int mid = (start + end) / 2;
        sort2(arr, start, mid);
        sort2(arr, mid + 1, end);

        int[] copyArr = new int[end - start + 1];
        System.arraycopy(arr, start, copyArr, 0, end - start + 1);

        int left = start;
        int right = mid + 1;

        for (int dest = start; dest <= end; dest++) {
            if (left > mid) {
                arr[dest] = copyArr[right - start];
                right++;
            } else if (right > end) {
                arr[dest] = copyArr[left - start];
                left++;
            } else if (copyArr[left - start] <= copyArr[right - start]) {
                arr[dest] = copyArr[left - start];
                left++;
            } else {
                arr[dest] = copyArr[right - start];
                right++;
            }
        }
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
        int arrLength = 20000;
        int[] arr = new int[arrLength];
        int[] arr1 = new int[arrLength];
        int[] arr2 = new int[arrLength];
        for (int i = 0; i < arrLength; i++) {
            arr[i] = new Random().nextInt(arrLength);
            arr1[i] = arr[i];
            arr2[i] = arr[i];
        }

        long time0 = System.currentTimeMillis();
        sort1(arr1, 0, arrLength - 1);
        long time1 = System.currentTimeMillis();
        sort(arr, 0, arrLength - 1);
        long time2 = System.currentTimeMillis();
        sort2(arr2, 0, arrLength - 1);
        long time3 = System.currentTimeMillis();

        System.out.println("sort1耗时：" + (time1 - time0) + "ms");
        System.out.println("sort耗时：" + (time2 - time1) + "ms");
        System.out.println("sort2耗时：" + (time3 - time2) + "ms");
        check(arr1);
        check(arr);
        check(arr2);
    }

}
