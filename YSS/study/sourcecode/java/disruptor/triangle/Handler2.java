package disruptor.triangle;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yuh
 * @date 2019-06-16 12:31
 **/
public class Handler2 implements EventHandler<OrderEvent> {


    private static final AtomicInteger at = new AtomicInteger();


    @Override
    public void onEvent(OrderEvent event, long sequence, boolean endOfBatch) throws Exception {
        event.setName("name_" + at.getAndIncrement());
    }
}
