package array;

import java.util.Arrays;

/**
 * 支持动态扩容的数组
 * 实现思想 当数组不够用的时候经数组长度扩展到2倍长度 当数组使用接近四分之一的时候 将数组长度缩减为1/2
 * 这样能缓解复杂度震荡
 *
 * @author yuh
 * @date 2019-05-27 07:33
 **/
public class DynamicArray {
    private final int minLen = 4;
    private int[] data;
    private int size;

    public DynamicArray() {
        data = new int[minLen];
    }

    public void add(int ele) {
        if (size == data.length) {
            resize(size * 2);
        }
        data[size++] = ele;
    }

    public int remove(int index) {
        int rt = data[index];
        if (index != size - 1) {
            System.arraycopy(data, index + 1, data, index, size - index - 1);
        }
        data[size - 1] = 0;
        size--;
        if (size <= data.length / 4 && data.length > minLen) {
            resize(data.length / 2 < minLen ? minLen : data.length / 2);
        }
        return rt;
    }

    private void resize(int newSize) {
        int[] newArr = new int[newSize];
        System.arraycopy(data, 0, newArr, 0, Math.min(newSize,data.length));
        data = newArr;
    }

    @Override
    public String toString() {
        return Arrays.toString(data);
    }

    public static void main(String[] args) {
        DynamicArray dynamicArray = new DynamicArray();
        for (int i = 0; i < 10; i++) {
            dynamicArray.add(i);
        }
        System.out.println(dynamicArray);
        for (int i = 0; i < 8; i++) {
            dynamicArray.remove(0);
        }
        System.out.println(dynamicArray);
    }

}

