package heap;

import tools.Utils;

public class MaxHeapSort2 {


    public static void sort(int[] arr) {
        int l = 0;
        int r = arr.length - 1;
        heapify(arr, l, r);
        Utils.swap(arr, l, r);
        r--;

        while (l < r) {
            shiftDown(0, arr, l, r);
            Utils.swap(arr, l, r);
            r--;
        }

    }

    private static void shiftDown(int index, int[] arr, int l, int r) {
        int arrLength = r - l;
        while (index * 2 + 1 <= arrLength) {

            int maybeSwapIndex = index * 2 + 1;
            if (index * 2 + 2 <= arrLength && arr[index * 2 + 2] > arr[index * 2 + 1]) {
                maybeSwapIndex = index * 2 + 2;
            }

            if (arr[index] < arr[maybeSwapIndex]) {
                Utils.swap(arr, maybeSwapIndex, index);
                index = maybeSwapIndex;
            } else {
                break;
            }
        }
    }

    public static void heapify(int[] arr, int l, int r) {
        for (int lastNoSonIndex = (r - l) / 2; lastNoSonIndex >= 0; lastNoSonIndex--) {
            shiftDown(lastNoSonIndex, arr, l, r);
        }
    }

    public static void main(String[] args) {
        int[] teacherData = {16, 15, 17, 19, 13, 22, 41, 28, 30, 62};
//        heapify(teacherData, 0, teacherData.length - 1);
        sort(teacherData);

        int[] newArr = new int[teacherData.length + 1];
        System.arraycopy(teacherData, 0, newArr, 1, teacherData.length);
        PrintHeap1.treePrint(newArr.length - 1, newArr);
    }

}
