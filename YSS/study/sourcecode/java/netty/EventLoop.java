package netty;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author yuh
 * @date 2019-06-03 15:59
 **/
public class EventLoop extends Thread {

    private Selector selector;
    List<SocketChannel> channels = new ArrayList<>();

    public EventLoop() {
        try {
            selector = SelectorProvider.provider().openSelector();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void submit(SocketChannel socketChannel) {
        channels.add(socketChannel);
        try {
            socketChannel.register(selector, SelectionKey.OP_READ);
            ByteBuffer wrap = ByteBuffer.wrap("dsf>".getBytes());
            wrap.flip();
            socketChannel.write(wrap);
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            for (; ; ) {
                int select = selector.select(100);
                if (select == 0) {
                    continue;
                }
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey next = keyIterator.next();
                    keyIterator.remove();
                    if (next.isReadable()) {
                        SocketChannel channel = (SocketChannel) next.channel();
                        SocketAddress remoteAddress = channel.getRemoteAddress();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                        int read = channel.read(byteBuffer);
                        System.out.println("from " + remoteAddress.toString() + ": " + new String(byteBuffer.array(), 0, read));
                        byteBuffer.put("\ndsf>".getBytes());
                        byteBuffer.flip();
                        channel.write(byteBuffer);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ;
        }
    }

    public int getCount(){
        return channels.size();
    }
}
