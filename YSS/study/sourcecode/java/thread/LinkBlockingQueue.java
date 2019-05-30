package thread;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 阻塞队列
 * @author yuh
 * @date 2019-05-30 11:38
 **/
public class LinkBlockingQueue<T> {

    private LinkedList<T> list = new LinkedList<>();
    private Lock lock = new ReentrantLock();
    private Condition notFULL = lock.newCondition();
    private Condition notEmpty = lock.newCondition();
    private int capacity;

    public LinkBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public void enqueue(T t) throws InterruptedException {
        lock.lock();
        System.out.println("currSize="+list.size());
        while(list.size() == capacity){
            notEmpty.signalAll();
            notFULL.await();
        }
        list.addFirst(t);
        notEmpty.signalAll();
        lock.unlock();
    }

    public T dequue() throws InterruptedException {
        lock.lock();
        System.out.println("currSize="+list.size());
        while(list.isEmpty()){
            notFULL.signalAll();
            notEmpty.await();
        }
        T t1 = list.removeLast();
        notFULL.signalAll();
        lock.unlock();
        return t1;
    }


    public static void main(String[] args) throws InterruptedException {
        LinkBlockingQueue<Integer> queue = new LinkBlockingQueue<>(5);
        Runnable p = new Runnable(){

            @Override
            public void run() {
                for (int i = 0; i < 10000000; i++) {
                    try {
                        System.out.println(Thread.currentThread().getName()+"生产:"+i);
                        queue.enqueue(i);
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread p1 = new Thread(p, "producer1");
        Thread p2 = new Thread(p, "producer2");
//        p1.start();
        p2.start();

        Thread.sleep(1000);


        Runnable c = new Runnable(){

            @Override
            public void run() {
                for (int i = 0; i < 10000000; i++) {
                    try {
                        System.out.println(Thread.currentThread().getName()+"消费:"+queue.dequue());
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread c1 = new Thread(c, "consumer1");
        Thread c2 = new Thread(c, "consumer2");

//        c1.start();
        c2.start();
    }



















}
