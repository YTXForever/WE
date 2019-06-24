package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

/**
 * @author yuh
 * @date 2019-06-15 10:08
 **/
public class Executor {


    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            executorService.execute(()->{
                try {
                    Thread.sleep(Integer.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        Thread.sleep(Integer.MAX_VALUE);


        SynchronousQueue<Integer> queue = new SynchronousQueue<>();
        for (int i = 0; i < 1000; i++) {
            System.out.println(queue.offer(i));
        }

    }
}
