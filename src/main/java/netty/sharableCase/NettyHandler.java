package netty.sharableCase;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;

import java.util.concurrent.atomic.AtomicInteger;

@ChannelHandler.Sharable
public class NettyHandler extends ChannelInboundHandlerAdapter {
    private AtomicInteger integer = new AtomicInteger(0);

    public NettyHandler() {
        System.out.println(this.getClass()
                .getSimpleName() + " init....");
    }

    // 从channel中读取消息时
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println(integer.incrementAndGet());

        ByteBuf in = (ByteBuf) msg;
        System.out.println(
                "Server received: " + in.toString(CharsetUtil.UTF_8));
        ctx.write(in);
    }

    // 读取完毕后的处理
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);// 关闭该Channel
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) {
        cause.printStackTrace();
        ctx.close(); // 关闭该channel
    }
}
