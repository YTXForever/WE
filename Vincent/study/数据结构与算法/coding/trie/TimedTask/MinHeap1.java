package TimedTask;

import heap.PrintHeap1;

public class MinHeap1<T extends Comparable<T>> {
    private Object[] dataArr;
    private int count;
    private int capacity;

    public void add(T data) {
        if (count == capacity) {
            System.out.println("已经装不下了~");
            return;
        }
        dataArr[count] = data;
        shiftUp(count);
        count++;
    }

    private void shiftUp(int index) {
        if (index == 0) {
            return;
        }
        if (((T) dataArr[index]).compareTo((T) dataArr[(index - 1) / 2]) < 0) {
            swap(index, (index - 1) / 2);
            shiftUp((index - 1) / 2);
        }
    }

    public T poll() {
        if (count == 0) {
            System.out.println("已经没有了~");
            return null;
        }
        T data = (T) dataArr[0];

        dataArr[0] = dataArr[count - 1];
        dataArr[count - 1] = null;
        count--;

        shiftDown(0);
        return data;
    }

    private void shiftDown(int index) {
        int maybeSwapIndex = index * 2 + 1;
        if (maybeSwapIndex + 1 > count) {
            return;
        }
        if (maybeSwapIndex + 1 < count
                && ((T) dataArr[maybeSwapIndex + 1]).compareTo((T) dataArr[maybeSwapIndex]) < 0) {
            maybeSwapIndex = maybeSwapIndex + 1;
        }

        if (((T) dataArr[maybeSwapIndex]).compareTo((T) dataArr[index]) < 0) {
            swap(index, maybeSwapIndex);
            shiftDown(maybeSwapIndex);
        }

    }

    public T peek() {
        return (T) dataArr[0];
    }

    public void print() {
        int[] newArr = new int[dataArr.length + 1];

        for (int i = 0; i < dataArr.length; i++) {
            newArr[i + 1] = (Integer) dataArr[i];
        }

        PrintHeap1.treePrint(count, newArr);
    }


    public static void main(String[] args) {
        int[] teacherData = {16, 15, 17, 19, 13, 22, 41, 28, 30, 62};

        MinHeap1<Integer> minHeap1 = new MinHeap1<Integer>(10);
        for (int i = 0; i < teacherData.length; i++) {
            minHeap1.add(teacherData[i]);
        }

        minHeap1.print();

        Integer poll = minHeap1.poll();
        System.out.println("poll = " + poll);

        minHeap1.print();

    }

    private void swap(int aIndex, int bIndex) {
        Object a = dataArr[aIndex];
        dataArr[aIndex] = dataArr[bIndex];
        dataArr[bIndex] = a;
    }

    public MinHeap1(int capacity) {
        this.dataArr = new Object[capacity];
        this.count = 0;
        this.capacity = capacity;
    }
}
