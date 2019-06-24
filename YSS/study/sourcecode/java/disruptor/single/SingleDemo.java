package disruptor.single;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.Executors;

/**
 * @author yuh
 * @date 2019-06-12 09:50
 **/
public class SingleDemo {

    public static void main(String[] args) {
        Disruptor<OrderEvent> disruptor = new Disruptor(
                new OrderEventFactory(),
                1024 * 1024,
                Executors.defaultThreadFactory(),
                ProducerType.SINGLE,
                new BlockingWaitStrategy());

        RingBuffer<OrderEvent> ringBuffer =
                disruptor.getRingBuffer();

        disruptor.handleEventsWith(new EventHandler<OrderEvent>() {
            @Override
            public void onEvent(OrderEvent event, long sequence, boolean endOfBatch) throws Exception {
                System.out.println("sequence:" + sequence + ",orderId:" + event.getId());
            }
        });
        disruptor.start();
        for (int i = 0; i < 1000; i++) {
            long next = ringBuffer.next();
            OrderEvent orderEvent = ringBuffer.get(next);
            orderEvent.setId(i);
            ringBuffer.publish(next);
        }
    }

}
