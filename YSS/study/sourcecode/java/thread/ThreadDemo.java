package thread;

import java.util.concurrent.Future;
import java.util.concurrent.locks.LockSupport;

/**
 * @author yuh
 * @date 2019-05-30 09:16
 **/
public class ThreadDemo {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread("YSS"){
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                }catch (Exception e){

                }
                System.out.println("运行");
            }
        };
        thread.start();
        thread.join();
        thread.start();
//        Future
//        thread.start();
//        System.out.println("====");
//        thread.start();
//        LockSupport.park();
//        synchronized (thread){
//            thread.wait(1);
//        }
//        System.out.println("老子执行完了");
//        LockSupport.parkNanos();
    }
}
