package socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author yuh
 * @date 2019-05-27 20:29
 **/
public class ClientSocket {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        socket.setTcpNoDelay(true);
        socket.connect(new InetSocketAddress("192.168.199.113", 8888));
        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String next = scanner.next();
            if ("end".equals(next)) {
                break;
            }
            outputStream.write(next.getBytes());
            outputStream.flush();
            byte[] arr = new byte[128];
            int read = inputStream.read(arr);
            System.out.println("from server length is " + new String(arr, 0, read));
        }
        inputStream.close();
        outputStream.close();
        socket.close();
    }
}
