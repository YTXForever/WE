package SelectSort;

import java.util.Random;

public class SelectSort1 {

    public static void sort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int min = arr[i];
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < min) {
                    min = arr[j];
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }

    public static void sort1(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minNumIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minNumIndex]) {
                    minNumIndex = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[minNumIndex];
            arr[minNumIndex] = temp;
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
        for (int i = 0; i < arrLength; i++) {
            arr[i] = new Random().nextInt(arrLength);
            arr1[i] = new Random().nextInt(arrLength);
        }

        long time0 = System.currentTimeMillis();
        sort(arr);
        long time1 = System.currentTimeMillis();
        sort1(arr1);
        long time2 = System.currentTimeMillis();

        System.out.println("sort耗时：" + (time1 - time0) + "ms");
        System.out.println("sort1耗时：" + (time2 - time1) + "ms");
    }
}
