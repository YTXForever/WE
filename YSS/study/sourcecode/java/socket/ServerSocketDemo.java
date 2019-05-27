package socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author yuh
 * @date 2019-05-27 20:29
 **/
public class ServerSocketDemo {

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8888);
        Socket accept = ss.accept();
        accept.setTcpNoDelay(true);
        OutputStream outputStream = accept.getOutputStream();
        InputStream inputStream = accept.getInputStream();
        while (true) {
            byte[] arr = new byte[128];
            int read = inputStream.read(arr);
            String s = new String(arr, 0, read);
            System.out.println("from client:" +s);
            if ("end".equals(s)) {
                break;
            }
            outputStream.write(String.valueOf(s.length()).getBytes());
            outputStream.flush();

        }
        accept.close();
    }
}
