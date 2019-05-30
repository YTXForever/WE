package stack;

/**
 * @author yuh
 * @date 2019-05-30 06:49
 **/
public class ArrayStack<T> {

    private T[] data;
    private int size;
    private int capacity;

    public ArrayStack(int capacity) {
        this.capacity = capacity;
        data = (T[]) new Object[capacity];
    }

    public void push(T ele) {
        if (size == capacity) {
            return;
        }
        data[size++] = ele;
    }

    public T pop() {
        if (size == 0) {
            return null;
        }
        return data[--size];
    }

    public boolean isEmpty(){
        return size==0;
    }


    public static void main(String[] args) {
        ArrayStack<String> stack = new ArrayStack<>(5);
        for (int i = 0; i < 5; i++) {
            stack.push(i+"");
        }
        for (int i = 0; i < 5; i++) {
            System.out.println(stack.pop());
        }
    }
}
