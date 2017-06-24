package org.dncf.server;

import org.dncf.handler.ChildChannelHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by LJT on 17-6-6.
 * email: ljt1343@gmail.com
 */
@Component
public class DNCNettyServer {
    private static Logger logger = LoggerFactory.getLogger(DNCNettyServer.class);

    @Autowired
    @Qualifier("serverBootstrap")
    private ServerBootstrap bootstrap;

    @Autowired
    @Qualifier("bossGroup")
    private EventLoopGroup bossGroup;

    @Autowired
    @Qualifier("workerGroup")
    private EventLoopGroup workerGroup;

//    @Autowired
//    private InetSocketAddress address;

    @Value("${netty.port:9003}")
    private int port;

    @Autowired
    private ChildChannelHandler initializer;

    private Channel channel;

    public void start() throws Exception {
        try {
            logger.info("[info] >>> start netty server.");
            bootstrap.group(bossGroup, workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.handler(new LoggingHandler(LogLevel.INFO));
            bootstrap.childHandler(initializer);
            // bootstrap.option(ChannelOption.SO_BACKLOG, 128);
            logger.info("[info] >>> set netty server params");
            bootstrap.option(ChannelOption.SO_BACKLOG, 65535);
            // 保持长连接状态
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            bootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);// 关键是这句

            bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
            bootstrap.childOption(ChannelOption.SO_REUSEADDR, true);
            ChannelFuture cf = bootstrap.bind(port).sync();
            logger.info("[info] >>> netty server bind port:[" + port + "]");

            channel = cf.channel();
            logger.info("[info] >>> netty server running...");
            channel.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[info] >>> netty server start fail.");
        } finally {
            destroy();
        }
    }

    public void destroy() {
        try {
            if (channel != null) {
                channel.close();
            }
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[info] >>> destroy netty server exception." + e.getMessage());
        }
    }
}
