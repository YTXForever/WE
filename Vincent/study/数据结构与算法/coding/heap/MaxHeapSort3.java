package heap;

import tools.Utils;

public class MaxHeapSort3 {

    public static void sort(int[] arr, int n) {

        for (int lastNoSonIndex = (n - 1) / 2; lastNoSonIndex >= 0; lastNoSonIndex--) {
            shiftDown(arr, n, lastNoSonIndex);
        }

        for (int i = n - 1; i > 0; i--) {
            Utils.swap(arr, 0, i);
            shiftDown(arr, i, 0);
        }
    }

    private static void shiftDown(int[] arr, int length, int index) {
        int arrIndexMax = length - 1;
        while (index * 2 + 1 <= arrIndexMax) {

            int maybeSwapIndex = index * 2 + 1;
            if (maybeSwapIndex + 1 <= arrIndexMax && arr[maybeSwapIndex + 1] > arr[maybeSwapIndex]) {
                maybeSwapIndex = maybeSwapIndex + 1;
            }

            if (arr[index] < arr[maybeSwapIndex]) {
                Utils.swap(arr, maybeSwapIndex, index);
                index = maybeSwapIndex;
            } else {
                break;
            }
        }
    }

    public static void main(String[] args) {
//        int[] teacherData = {16, 15, 17, 19, 13, 22, 41, 28, 30, 62};
        int[] teacherData = Utils.getNewArr(10000000);

        long time0 = System.currentTimeMillis();
        sort(teacherData, teacherData.length);
        long time1 = System.currentTimeMillis();


        System.out.println(" 耗时：" + (time1 - time0) + "ms");
        Utils.checkAsc(teacherData, "MaxHeapSort");

    }

}
