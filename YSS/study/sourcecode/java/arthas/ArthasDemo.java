package arthas;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author yuh
 * @date 2019-06-18 13:09
 **/
public class ArthasDemo {


    static class Node {
        ScheduledFuture future;
        long time;
    }

    public static void main(String[] args) throws InterruptedException {
        Bar bar = new Bar();


        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        final Node node = new Node();

        ScheduledFuture<?> schedule = service.schedule(() -> {
            node.future = null;
            node.time = System.currentTimeMillis();
        }, 5, TimeUnit.SECONDS);

        while (true) {
            bar.foo();
            Thread.sleep(1000);
        }
    }
}
