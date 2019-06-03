package netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author yuh
 * @date 2019-06-03 16:35
 **/
public class ClientDemo {

    public static void main(String[] args) throws IOException, InterruptedException {
        for (int i = 0; i < 1000; i++) {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(8888));
        }
        Thread.sleep(Integer.MAX_VALUE);
    }
}
