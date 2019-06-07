package IndexHeap;

import tools.Utils;

import java.util.Arrays;

public class IndexHeap1<T extends Comparable<T>> {

    private int[] indexArr;
    private Object[] dataArr;
    private int capacity;
    private int count;// 当前数量，指向的是下一个空闲下标

    public void add(int data) {
        if (count + 1 > capacity - 1) {
            System.out.println("装不下了~" + data);
            return;
        }
        dataArr[count] = data;
        indexArr[count] = count;
        count++;
        shiftUp(count - 1);
    }

    private void shiftUp(int index) {
        while (index > 0 && ((T) dataArr[indexArr[(index - 1) / 2]]).compareTo((T) dataArr[indexArr[index]]) < 0) {
            Utils.swap(indexArr, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    public T poll() {
        if (count == 0) {
            System.out.println("实在没有了~");
            return null;
        }
        T pollData = (T) dataArr[indexArr[0]];
        count--;
        indexArr[0] = indexArr[count];

        shiftDown(0);
        return pollData;
    }

    private void shiftDown(int index) {
        while (index * 2 + 1 < count) {

            int maybeSwapIndex = index * 2 + 1;
            if (maybeSwapIndex + 1 < count && ((T) dataArr[indexArr[maybeSwapIndex + 1]]).compareTo((T) dataArr[indexArr[maybeSwapIndex]]) > 0) {
                maybeSwapIndex = maybeSwapIndex + 1;
            }

            if (((T) dataArr[indexArr[index]]).compareTo((T) dataArr[indexArr[maybeSwapIndex]]) < 0) {
                Utils.swap(indexArr, maybeSwapIndex, index);
                index = maybeSwapIndex;
            } else {
                break;
            }
        }
    }


    public static void main(String[] args) {
        Integer[] teacherData = {16, 15, 17, 19, 13};
        IndexHeap1<Integer> indexHeap1 = new IndexHeap1(20);
        for (int i = 0; i < teacherData.length; i++) {
            indexHeap1.add(teacherData[i]);
        }

        System.out.println(Arrays.toString(indexHeap1.getIndexArr()));
        Integer pollNum = null;
        while ((pollNum = indexHeap1.poll()) != null) {
            System.out.print(pollNum + ",");
        }


    }

    public IndexHeap1(int capacity) {
        this.indexArr = new int[capacity];
        this.dataArr = new Object[capacity];
        this.capacity = capacity;
        this.count = 0;
    }

    public int[] getIndexArr() {
        return indexArr;
    }
}
