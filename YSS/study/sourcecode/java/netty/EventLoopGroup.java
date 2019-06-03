package netty;

import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yuh
 * @date 2019-06-03 16:06
 **/
public class EventLoopGroup {

    private EventLoop[] arr = new EventLoop[16];
    private AtomicInteger atomicLong = new AtomicInteger(0);

    public EventLoopGroup() {
        for (int i = 0; i < arr.length; i++) {
            EventLoop eventLoop = new EventLoop();
            eventLoop.start();
            arr[i] = eventLoop;
        }
        new Thread() {
            @Override
            public void run() {
                for (; ; ) {
                    int sum = 0;
                    for (int i = 0; i < arr.length; i++) {
                        sum += arr[i].getCount();
                    }
                    System.out.println("Current Conn is " + sum);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public EventLoop next() {

        return arr[atomicLong.getAndIncrement() & (arr.length - 1)];
    }


}
