package inttt;

import javax.smartcardio.ATR;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yuh
 * @date 2019-06-05 22:16
 **/
public class IntTest {

    public static void a(AtomicInteger a){
        a.incrementAndGet();
    }

    public static void main(String[] args) {
        a(new AtomicInteger());

    }
}
