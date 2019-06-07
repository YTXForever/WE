package tools;

import java.util.Random;

public class Utils {

    public static int[] getNewArr(int length) {
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            arr[i] = new Random().nextInt(length);
        }
        return arr;
    }

    public static int[] copyArr(int[] oldArr) {
        int[] newArr = new int[oldArr.length];
        System.arraycopy(oldArr, 0, newArr, 0, oldArr.length);
        return newArr;
    }

    public static int[] getNearSortArr(int length, int swap) {
        int[] arr = new int[length];
        for (int i = 0; i < length; i++) {
            arr[i] = i;
        }
        for (int i = 0; i < swap; i++) {
            int swap1 = new Random().nextInt(length);
            int swap2 = new Random().nextInt(length);
            swap(arr, swap1, swap2);
        }
        return arr;
    }

    public static void swap(int[] arr, int aIndex, int bIndex) {
        int temp = arr[aIndex];
        arr[aIndex] = arr[bIndex];
        arr[bIndex] = temp;
    }

    public static void checkAsc(int[] arr, String name) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                System.out.println("校验不通过:" + name);
                return;
            }
        }
        System.out.println("校验通过:" + name);
    }

    public static void checkDesc(int[] arr, String name) {
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] < arr[i + 1]) {
                System.out.println("校验不通过:" + name);
                return;
            }
        }
        System.out.println("校验通过:" + name);
    }


}
