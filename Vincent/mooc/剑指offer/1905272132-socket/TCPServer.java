package socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(8888);
            System.out.println("启动服务器....");

            Socket s = ss.accept();
            String hostAddress = s.getInetAddress().getHostAddress();

            System.out.println("客户端:" + hostAddress + "已连接到服务器");
            System.out.println("客户端:" + s.getInetAddress().getLocalHost() + "已连接到服务器");

            InputStream inputStream = s.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));

//            br.readLine();
            while (true) {
                //读取客户端发送来的消息
                byte[] arr = new byte[128];
                int read = inputStream.read(arr);
                String mess = new String(arr, 0, read);
                System.out.println("客户端：" + mess);
                bw.write(mess + "\n");
                bw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
