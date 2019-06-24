package disruptor.triangle;

import com.lmax.disruptor.EventHandler;

/**
 * @author yuh
 * @date 2019-06-16 12:31
 **/
public class Handler4 implements EventHandler<OrderEvent> {
    @Override
    public void onEvent(OrderEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println(event);
    }

}
