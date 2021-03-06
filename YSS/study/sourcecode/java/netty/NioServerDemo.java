package netty;

import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.AbstractSelector;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;

/**
 * NioServer
 *
 * @author yuh
 * @date 2019-06-03 15:24
 **/
public class NioServerDemo {

    public static void main(String[] args) throws IOException {
        EventLoopGroup eventLoopGroup = new EventLoopGroup();
        SelectorProvider provider = SelectorProvider.provider();
        AbstractSelector selector = provider.openSelector();
        ServerSocketChannel serverSocketChannel = provider.openServerSocketChannel();
        serverSocketChannel.configureBlocking(false);
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        serverSocketChannel.bind(new InetSocketAddress(8888));
        for (; ; ) {
            int select = selector.select();
            if (select == 0) {
                continue;
            }
            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()) {
                SelectionKey next = keyIterator.next();
                keyIterator.remove();
                if (next.isAcceptable()) {
                    SocketChannel accept = serverSocketChannel.accept();
                    accept.configureBlocking(false);
                    eventLoopGroup.next().submit(accept);
                }
            }
        }
    }
}
