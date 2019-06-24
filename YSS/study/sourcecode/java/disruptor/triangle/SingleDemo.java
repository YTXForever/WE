package disruptor.triangle;

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

        disruptor.handleEventsWith(new Handler1(), new Handler2()).handleEventsWith(new Handler3()).handleEventsWith(new Handler4());
        disruptor.start();
        for (int i = 0; i < 1000; i++) {
            ringBuffer.publish(ringBuffer.next());
        }
    }

}
