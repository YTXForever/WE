package disruptor.multi;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yuh
 * @date 2019-06-17 08:30
 **/
public class Main {

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        RingBuffer<OrderEvent> ringBuffer = RingBuffer.create(ProducerType.MULTI, new EventFactory<OrderEvent>() {
            @Override
            public OrderEvent newInstance() {
                return new OrderEvent();
            }
        }, 1024 * 1024, new BlockingWaitStrategy());

        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();

        WorkHandler[] handlers = new Handler[10];
        for (int i = 0; i < 10; i++) {
            handlers[i] = new Handler();
        }

        WorkerPool<OrderEvent> workerPool = new WorkerPool<OrderEvent>(ringBuffer, sequenceBarrier, new ExceptionHandler<OrderEvent>() {
            @Override
            public void handleEventException(Throwable ex, long sequence, OrderEvent event) {

            }

            @Override
            public void handleOnStartException(Throwable ex) {

            }

            @Override
            public void handleOnShutdownException(Throwable ex) {

            }
        }, handlers);

        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());
        workerPool.start(Executors.newFixedThreadPool(5));
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10);
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    for (int j = 0; j < 100; j++) {
                        ringBuffer.publish(ringBuffer.next());
                    }
                }
            }).start();
        }

        Thread.sleep(1000);
    }
}
