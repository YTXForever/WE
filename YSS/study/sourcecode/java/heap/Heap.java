package heap;

/**
 * 堆
 *
 * @author yuh
 * @date 2019-06-04 09:21
 **/
public class Heap<T extends Comparable<T>> {

    private T[] data;
    private int size;
    private int capacity;

    public Heap(int capacity) {
        this.capacity = capacity;
        data = (T[]) new Comparable[capacity];
    }

    public boolean add(T t) {
        if (size == capacity) {
            return false;
        }
        data[size] = t;
        shiftUp(size);
        size++;
        return true;
    }

    private void shiftUp(int index) {
        while (index > 0 && data[index].compareTo(data[index / 2]) < 0) {
            swap(index, index / 2);
            index /= 2;
        }
    }

    public T remove() {
        if (size == 0) {
            return null;
        }
        T rt = data[0];
        data[0] = data[size - 1];
        data[size - 1] = null;
        size--;
        shiftDown(0);
        return rt;
    }

    private void shiftDown(int index) {
        while (index * 2 + 1 < size) {
            int k = index * 2 + 1;
            if (k + 1 < size && data[k + 1].compareTo(data[k]) < 0) {
                k++;
            }
            if (data[k].compareTo(data[index]) >= 0) {
                break;
            }
            swap(index, k);
            index = k;
        }
    }

    public T get() {
        if (size == 0) {
            return null;
        }
        return data[0];
    }

    private void swap(int i, int j) {
        T tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size(){
        return size;
    }

    public static void main(String[] args) {
        Heap<Integer> heap = new Heap<>(5);
        for (int i = 0; i < 5; i++) {
            heap.add(5-i);
        }
        while(!heap.isEmpty()){
            System.out.println(heap.remove());
        }
    }
}
