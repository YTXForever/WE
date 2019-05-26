package array;

import java.util.Arrays;

/**
 * 有序的数组
 * 实现思想 插入时寻找合适位置 插入到比这个数字大的那个数字前面 或者size位置
 *
 * @author yuh
 * @date 2019-05-27 06:27
 **/
public class SortedArray {

    private int capacity;
    private int size;
    private int[] data;
    private int max = Integer.MIN_VALUE;

    public SortedArray(int capacity) {
        this.capacity = capacity;
        this.data = new int[capacity];
    }


    public boolean add(int ele) {
        if (size == capacity) {
            return false;
        }
        if (size == 0 || ele >= max) {
            data[size++] = ele;
            if (ele > max) {
                max = ele;
            }
            return true;
        }
        int index = binarySearchGt(0, size - 1, ele);
        //-1的情况的在前面已经做了判断
        if (index > 0) {
            //shift
            System.arraycopy(data, index, data, index + 1, size - index);
            data[index] = ele;
            size++;
        }
        return true;
    }

    public int remove(int index) {
        int rt = data[index];
        //direvct override
        System.arraycopy(data, index + 1, data, index, size - index - 1);
        data[size-1]=0;
        size--;
        return rt;
    }

    //找第一比ele大的数
    private int binarySearchGt(int start, int end, int k) {
        while (start <= end) {
            int mid = (start + end) >> 1;
            int curr = data[mid];
            if (curr <= k) {
                start = mid + 1;
            } else if (mid == 0 || data[mid-1] <= k) {
                return mid;
            } else {
                end = mid - 1;
            }
        }
        return -1;
    }

    @Override
    public String toString(){
        return Arrays.toString(data);
    }

    public static void main(String[] args) {
        SortedArray sortedArray = new SortedArray(5);
        for (int i = 0; i < 5; i++) {
            sortedArray.add(i % 3);
        }
        System.out.println(sortedArray);
        sortedArray.remove(1);
        System.out.println(sortedArray);
    }
}
