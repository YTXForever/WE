package socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

/**
 * @author yuh
 * @date 2019-05-27 21:18
 **/
public class UdpServerSocket {

    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket(8887);
        while(true){
            byte[] buf = new byte[128];
            DatagramPacket dp = new DatagramPacket(buf,buf.length);
            datagramSocket.receive(dp);
            byte[] data = dp.getData();
            String s = new String(data, 0, dp.getLength());
            System.out.println("from client : "+s);
            byte[] bytes = String.valueOf(s.length()).getBytes();
            DatagramPacket dp1 = new DatagramPacket(bytes,bytes.length,dp.getAddress(),dp.getPort());
            datagramSocket.send(dp1);
        }
    }
}
