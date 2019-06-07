package MergeSort;

import java.util.Random;

public class MergeSort2 {

    public static void sort(int[] arr) {
        for (int sz = 1; sz < arr.length; sz *= 2) {
            for (int j = 0; j + sz < arr.length; j += (sz * 2)) {
                merge(arr, j, j + sz - 1, Math.min(arr.length - 1, j + sz + sz - 1));
            }
        }
    }

    /**
     * arr[start, mid] 与 arr[mid + 1, end] 归并排序
     */
    public static void merge(int[] arr, int start, int mid, int end) {
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
        for (int i = 0; i < arrLength; i++) {
            arr[i] = new Random().nextInt(arrLength);
        }

        long time2 = System.currentTimeMillis();
        sort(arr);
        long time3 = System.currentTimeMillis();

        System.out.println("sort耗时：" + (time3 - time2) + "ms");
        check(arr);
    }

}
