package socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;


class UDPClient {
    public static void main(String[] args) throws IOException {
        DatagramSocket client = new DatagramSocket();

        InetAddress addr = InetAddress.getByName("192.168.199.193");
        int port = 8887;

        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("请输要发送的内容：");

            String str = sc.nextLine();
            byte[] sendBuf = str.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, addr, port);
            client.send(sendPacket);

            byte[] receBuf = new byte[1024];
            DatagramPacket recvPacket = new DatagramPacket(receBuf, receBuf.length);
            client.receive(recvPacket);

            String recvStr = new String(recvPacket.getData(), 0, recvPacket.getLength());
            System.out.println("收到:" + recvStr);

            if (str.endsWith("q") || str.endsWith("quit")) {
                break;
            }
        }
        client.close();
    }
}
