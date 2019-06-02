package queue;

/**
 * @author yuh
 * @date 2019-05-31 07:48
 **/
public class ArrCycleQueue<T> {

    private T[] data;
    private int addIndex;
    private int takeIndex;
    private int capacity;
    private int size;


    public ArrCycleQueue(int capacity) {
        this.capacity = capacity;
        data = (T[])new Object[capacity];
    }

    public boolean enqueue(T t){
        if(size == capacity){
            return false;
        }
        data[addIndex] = t;
        if(++addIndex == capacity){
            addIndex = 0;
        }
        size++;
        return true;
    }

    public T dequeue(){
        if(size == 0){
            return null;
        }
        T rt = data[takeIndex];
        if(++takeIndex == capacity){
            takeIndex = 0;
        }
        size--;
        return rt;
    }

    public static void main(String[] args) {
        ArrCycleQueue<Integer> queue = new ArrCycleQueue<>(5);
        for (int i = 0; i < 5; i++) {
            queue.enqueue(i);
        }
//        queue.dequeue();
        System.out.println(queue.enqueue(5));
        for (int i = 0; i < 5; i++) {
            System.out.println(queue.dequeue());
        }
    }
}
