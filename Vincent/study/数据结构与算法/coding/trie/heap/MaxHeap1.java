package heap;

import tools.Utils;

public class MaxHeap1 {

    private int[] arr;
    private int count;
    private int capacity;

    public void add(int data) {
        if (count + 1 > capacity) {
            System.out.println("装不下了~" + data);
            return;
        }
        arr[++count] = data;
        shiftUp(count);
    }

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
        PrintHeap1.treePrint(count, arr);
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

    public static void main(String[] args) {
        int[] teacherData = {16, 15, 17, 19, 13, 22, 41, 28, 30, 62, 12};
//        int[] teacherData = Utils.getNewArr(20);

        MaxHeap1 maxHeap1 = new MaxHeap1(20);
        for (int i = 0; i < teacherData.length; i++) {
            maxHeap1.add(teacherData[i]);
        }

        maxHeap1.add(52);
        PrintHeap1.treePrint(maxHeap1);

        int pollNum = -1;
        while ((pollNum = maxHeap1.poll()) > -1) {
            System.out.print(pollNum + ",");
        }


    }

    public MaxHeap1(int capacity) {
        this.arr = new int[capacity + 1];
        this.count = 0;
        this.capacity = capacity;
    }

    public int[] getArr() {
        return arr;
    }

    public int getCount() {
        return count;
    }
}
