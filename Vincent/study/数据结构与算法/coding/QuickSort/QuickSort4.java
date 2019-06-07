package QuickSort;

import tools.Utils;

import java.util.Random;

public class QuickSort4 {

    public static int getByIndexAfterSorted(int[] arr, int index) {
        int l = 0;
        int r = arr.length - 1;
        int partitionIndex = -1;
        while (l < r && partitionIndex != index) {
            partitionIndex = partition(arr, l, r);
            if (index < partitionIndex) {
                r = partitionIndex - 1;
            } else {
                l = partitionIndex + 1;
            }
        }
        return arr[index];
    }

    private static int partition(int[] arr, int start, int end) {
        int vIndex = new Random().nextInt(end - start) + start;
        Utils.swap(arr, vIndex, start);

        int l = start;
        int v = arr[l];

        int j = start;
        int i = start + 1;
        for (; i <= end; i++) {
            if (arr[i] < v) {
                Utils.swap(arr, i, j + 1);
                j++;
            }
        }
        Utils.swap(arr, l, j);
        return j;
    }


    public static void main(String[] args) {
        int arrLength = 1000000;
        int[] arr1 = Utils.getNewArr(arrLength);
        int[] arr2 = Utils.copyArr(arr1);

        int randomIndex = new Random().nextInt(arrLength);


        long time0 = System.currentTimeMillis();
        QuickSort3.sort1(arr1, 0, arrLength - 1);
        int arr1Result = arr1[randomIndex];
        long time1 = System.currentTimeMillis();
        int arr2Result = QuickSort4.getByIndexAfterSorted(arr2, randomIndex);
        long time2 = System.currentTimeMillis();

        System.out.println("arr1Result = " + arr1Result + " 耗时：" + (time1 - time0) + "ms");
        System.out.println("arr2Result = " + arr2Result + " 耗时：" + (time2 - time1) + "ms");

    }

}
