package heap;

/**
 * @author yuh
 * @date 2019-06-04 10:25
 **/
public class PriorityQueue<T extends Comparable<T>> {

    private int capacity;
    private Heap<T> heap;

    public PriorityQueue(int capacity) {
        this.capacity = capacity;
        heap = new Heap<>(capacity);
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public boolean offer(T t) {
        return heap.add(t);
    }

    public T peek() {
        return heap.get();
    }

    public T pop() {
        return heap.remove();
    }

    public int size() {
        return heap.size();
    }
}
