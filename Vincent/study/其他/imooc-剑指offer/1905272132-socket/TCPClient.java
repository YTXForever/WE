package socket;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TCPClient {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("192.168.199.193", 8888);
            InputStream inputStream = s.getInputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            Scanner scanner = new Scanner(System.in);

            while (true) {
                bw.write(scanner.nextLine());
                bw.flush();

                //读取服务器返回的消息
                byte[] arr = new byte[128];
                int read = inputStream.read(arr);
                String mess = new String(arr, 0, read);
                System.out.println("服务器：" + mess);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
