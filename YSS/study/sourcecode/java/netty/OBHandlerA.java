package netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.util.concurrent.TimeUnit;

/**
 * outBound事件从尾部开始传播 直到write写出
 * @author yuh
 * @date 2019-06-04 18:42
 **/
public class OBHandlerA extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("OBHandlerA:"+msg);
        ctx.writeAndFlush(msg);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                ctx.channel().writeAndFlush("Hello World");
            }
        },3,TimeUnit.SECONDS);
    }
}
