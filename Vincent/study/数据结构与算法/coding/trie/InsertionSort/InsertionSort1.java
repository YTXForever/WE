package InsertionSort;

import java.util.Random;

public class InsertionSort1 {
    public static void sort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (arr[j + 1] >= arr[j]) {
                    break;
                } else {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    public static void sort1(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (arr[j - 1] <= arr[j]) {
                    break;
                } else {
                    int temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                }
            }
        }
    }

    public static void sort2(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int iNum = arr[i];
            int j = i;
            for (; j > 0 && arr[j - 1] > iNum; j--) {
                arr[j] = arr[j - 1];
            }
            arr[j] = iNum;
        }
    }

    public static void check(int[] arr, String name) {
        for (int i = 0; i < arr.length - 1; i++) {
            int min = arr[i];
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < min) {
                    System.out.println("校验不通过:" + name);
                    return;
                }
            }
        }
        System.out.println("校验通过:" + name);
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
        sort(arr);
        long time1 = System.currentTimeMillis();
        sort1(arr1);
        long time2 = System.currentTimeMillis();
        sort2(arr2);
        long time3 = System.currentTimeMillis();
        System.out.println("sort耗时：" + (time1 - time0) + "ms");
        System.out.println("sort1耗时：" + (time2 - time1) + "ms");
        System.out.println("sort2耗时：" + (time3 - time2) + "ms");
        check(arr, "sort");
        check(arr1, "sort");
        check(arr2, "sort2");
    }

}
