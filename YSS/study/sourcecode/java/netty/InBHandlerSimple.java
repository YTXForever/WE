package netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author yuh
 * @date 2019-06-04 18:42
 **/
public class InBHandlerSimple extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("InBHandlerSimple:"+msg);
        ctx.fireChannelRead(msg);
    }


}
