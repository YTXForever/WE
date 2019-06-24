package disruptor.triangle;

import com.lmax.disruptor.EventFactory;

/**
 * @author yuh
 * @date 2019-06-12 09:55
 **/
public class OrderEventFactory implements EventFactory<OrderEvent> {
    @Override
    public OrderEvent newInstance() {
        return new OrderEvent();
    }
}
