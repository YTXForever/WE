package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

/**
 * @author yuh
 * @date 2019-06-03 09:56
 **/
public class NettyServerDemo {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .childAttr(AttributeKey.valueOf("name"), "child")
                .handler(new ChannelInboundHandlerAdapter() {
                    @Override
                    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("added");
                    }

                    @Override
                    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("registed");
                    }

                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("active");
                    }
                })
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel socketChannel) throws Exception {
//                        socketChannel.pipeline().addLast(new InBHandlerSimple());
                        socketChannel.pipeline().addLast(new InBHandlerA());
                        socketChannel.pipeline().addLast(new InBHandlerB());
                        socketChannel.pipeline().addLast(new InBHandlerC());
                        socketChannel.pipeline().addLast(new InBHandlerEx());
//                        socketChannel.pipeline().addLast(new OBHandlerA());
//                        socketChannel.pipeline().addLast(new OBHandlerB());
//                        socketChannel.pipeline().addLast(new OBHandlerC());
                    }
                });

        ChannelFuture future = bootstrap.bind(8888).sync();
        future.channel().closeFuture().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }
        });

    }

}
