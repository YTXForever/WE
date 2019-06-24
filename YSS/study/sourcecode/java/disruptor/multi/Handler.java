package disruptor.multi;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import disruptor.triangle.OrderEvent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yuh
 * @date 2019-06-16 12:31
 **/
public class Handler implements WorkHandler<OrderEvent> {


    @Override
    public void onEvent(OrderEvent event) throws Exception {
        System.out.println(event);
    }
}
