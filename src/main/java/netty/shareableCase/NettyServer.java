package netty.shareableCase;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class NettyServer {
    private final int port;

    public NettyServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        new NettyServer(20000).start();
    }

    public void start() throws Exception {
        final NettyHandler serverHandler
                = new NettyHandler();// 可以被共享的handler
        EventLoopGroup group = new NioEventLoopGroup();// 创建 EventLoopGroup
        try {
            ServerBootstrap b = new ServerBootstrap();// 创建ServerBootstrap
            b.group(group)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline()
                                    .addLast(serverHandler);// ShareableEchoServerHandler 被标注为@Shareable，所以我们可以总是使用同样的实例
                            //                       .addLast(new NoShareableEchoServerHandler());
                        }
                    });
            ChannelFuture f = b.bind().sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}
