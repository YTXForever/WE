package queue;

/**
 * @author yuh
 * @date 2019-05-31 07:24
 **/
public class ArrayQueue<T> {

    private T[] data;
    private int size;
    private int capacity;
    private int head;

    public ArrayQueue(int capacity) {
        this.capacity = capacity;
        data = (T[]) new Object[capacity];
    }


    public boolean enqueue(T e) {
        if (size == capacity) {
            if (head == 0) {
                return false;
            }
            //shift
            System.arraycopy(data, head, data, 0, size - head);
            size -= head;
            head = 0;
        }
        data[size++] = e;
        return true;
    }

    public T dequeue() {
        if (size == head) {
            return null;
        }
        return data[head++];
    }

    public static void main(String[] args) {
        ArrayQueue<Integer> queue = new ArrayQueue<>(5);
        for (int i = 0; i < 5; i++) {
            queue.enqueue(i);
        }
        queue.dequeue();
        System.out.println(queue.enqueue(5));
        for (int i = 0; i < 5; i++) {
            System.out.println(queue.dequeue());
        }
    }
}
