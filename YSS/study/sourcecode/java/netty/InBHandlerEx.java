package netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author yuh
 * @date 2019-06-04 18:42
 **/
public class InBHandlerEx extends ChannelInboundHandlerAdapter {


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("a ex has been caught:"+cause.getMessage());
//        ctx.fireExceptionCaught(cause);
    }

}
