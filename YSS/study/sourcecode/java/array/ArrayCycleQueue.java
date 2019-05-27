package array;

/**
 * 循环队列
 *
 * @author yuh
 * @date 2019-05-27 08:39
 **/
public class ArrayCycleQueue {

    private int capacity;
    private int head;
    private int tail;
    private int size;
    private int[] data;

    public ArrayCycleQueue(int capacity) {
        this.capacity = capacity;
        data = new int[capacity + 1];
    }

    public boolean add(int ele) {
        if ((head + 1) % data.length == tail) {
            return false;
        }
        data[head] = ele;
        head = (head + 1) % data.length;
        size++;
        return true;
    }

    public int remove() {
        if (tail == head) {
            return -1;
        }
        int rt = data[tail];
        data[tail] = 0;
        tail = (tail + 1) % data.length;
        size--;
        return rt;
    }

    public static void main(String[] args) {
        ArrayCycleQueue arrayCycleQueue = new ArrayCycleQueue(5);
        for (int i = 0; i < 10; i++) {
            System.out.println(arrayCycleQueue.add(i));
        }

        for (int i = 0; i < 10; i++) {
            System.out.println(arrayCycleQueue.remove());
        }
    }
}
