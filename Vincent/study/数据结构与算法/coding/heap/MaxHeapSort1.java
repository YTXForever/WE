package heap;

import tools.Utils;

public class MaxHeapSort1 {
    private int[] arr;
    private int count;
    private int capacity;

    private void shiftUp(int index) {
        while (index > 1 && arr[index / 2] < arr[index]) {
            Utils.swap(arr, index, index / 2);
            index /= 2;
        }
    }

    public int poll() {
        if (count < 1) {
            System.out.println("实在没有了~");
            return -1;
        }
        int pollNum = arr[1];
        arr[1] = arr[count--];

        shiftDown(1);
        return pollNum;
    }

    private void shiftDown(int index) {
        while (index * 2 <= count) {

            int maybeSwapIndex = index * 2;
            if (index * 2 + 1 <= count && arr[index * 2 + 1] > arr[index * 2]) {
                maybeSwapIndex = index * 2 + 1;
            }

            if (arr[index] < arr[maybeSwapIndex]) {
                Utils.swap(arr, maybeSwapIndex, index);
                index = maybeSwapIndex;
            } else {
                break;
            }
        }
    }

    public MaxHeapSort1(int[] arr) {
        int[] newArr = new int[arr.length + 1];
        System.arraycopy(arr, 0, newArr, 1, arr.length);
        this.arr = newArr;
        this.count = arr.length;
        this.capacity = arr.length;
    }

    public void sort() {
        int lastNoSonIndex = count / 2;
        while (lastNoSonIndex >= 1) {
            shiftDown(lastNoSonIndex);
            lastNoSonIndex--;
        }
    }

    public static void main(String[] args) {
//        int[] teacherData = {16, 15, 17, 19, 13, 22, 41, 28, 30, 62};
        int[] teacherData = Utils.getNewArr(10000000);

        long time0 = System.currentTimeMillis();
        MaxHeapSort1 maxHeapSort1 = new MaxHeapSort1(teacherData);
        maxHeapSort1.sort();
        int[] sortArr = new int[teacherData.length];
        for (int i = 0; i < teacherData.length; i++) {
            sortArr[i] = maxHeapSort1.poll();
        }
        long time1 = System.currentTimeMillis();

        System.out.println(" 耗时：" + (time1 - time0) + "ms");
        Utils.checkDesc(sortArr, "MaxHeapSort");

    }
}
