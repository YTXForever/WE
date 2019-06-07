package teacherwang;

/**
 * 索引堆
 * 为了解决更新后重新排序的问题
 * 可以把元素的限制在一个范围内减少无用pop 例如dijstra算法
 *
 * @author yuh
 * @date 2019-06-04 09:21
 **/
public class IndexHeap<T extends Comparable<T>> {

    private T[] data;
    private int[] indexes;
    private int[] reverse;
    private int size;
    private int capacity;

    public IndexHeap(int capacity) {
        this.capacity = capacity;
        data = (T[]) new Comparable[capacity];
        indexes = new int[capacity];
        reverse = new int[capacity];
    }

    public boolean add(int index, T t) {
        if (index >= capacity || size == capacity) {
            return false;
        }
        data[index] = t;
        indexes[size] = index;
        reverse[index] = size;
        shiftUp(size);
        size++;
        return true;
    }

    private void shiftUp(int index) {
        while (index > 0 && data[indexes[index]].compareTo(data[indexes[index / 2]]) < 0) {
            swap(index, index / 2);
            index /= 2;
        }
    }

    public T removeElement() {
        if (size == 0) {
            return null;
        }
        int index = indexes[0];
        T rt = data[index];
        data[index] = null;
        indexes[0] = indexes[size - 1];
        indexes[size - 1] = 0;
        size--;
        shiftDown(0);
        return rt;
    }

    private void shiftDown(int index) {
        while (index * 2 + 1 < size) {
            int k = index * 2 + 1;
            if (k + 1 < size && data[indexes[k + 1]].compareTo(data[indexes[k]]) < 0) {
                k++;
            }
            if (data[indexes[k]].compareTo(data[indexes[index]]) >= 0) {
                break;
            }
            swap(index, k);
            index = k;
        }
    }

    public T get(int index) {
        if (index >= capacity) {
            return null;
        }
        return data[index];
    }

    public int removeIndex() {
        if (size == 0) {
            return -1;
        }
        int index = indexes[0];
        data[index] = null;
        indexes[0] = indexes[size - 1];
        indexes[size - 1] = 0;
        size--;
        shiftDown(0);
        return index;
    }

    public int peekIndex() {
        if (size == 0) {
            return -1;
        }
        return indexes[0];
    }

    public T peekElement() {
        if (size == 0) {
            return null;
        }
        return data[indexes[0]];
    }

    private void swap(int i, int j) {
        int tmp = indexes[i];
        indexes[i] = indexes[j];
        indexes[j] = tmp;

        reverse[indexes[i]] = i;
        reverse[indexes[j]] = j;
    }

    public boolean contains(int index){
        return data[index] != null;
    }

    public void change(int index,T t){
        data[index] = t;
        int i = reverse[index];
        shiftUp(i);
        shiftDown(i);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public static void main(String[] args) {
        IndexHeap<Integer> indexHeap = new IndexHeap<>(5);
        indexHeap.add(3,30);
        indexHeap.add(2,2);
        indexHeap.add(1,10);
        indexHeap.add(0,0);

        while(!indexHeap.isEmpty()){
            System.out.println(indexHeap.removeIndex());
        }
    }
}
