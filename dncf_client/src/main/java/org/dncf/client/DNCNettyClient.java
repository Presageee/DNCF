package org.dncf.client;

import org.dncf.handler.ChildChannelHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;

import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;


/**
 * Created by LJT on 17-6-6.
 * email: ljt1343@gmail.com
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DNCNettyClient {
    private static Logger logger = LoggerFactory.getLogger(DNCNettyClient.class);

    @Autowired
    @Qualifier("bootstrap")
    private Bootstrap bootstrap;

    @Autowired
    @Qualifier("workerGroup")
    private EventLoopGroup workerGroup;

    @Autowired
    private InetSocketAddress address;

    @Autowired
    private ChildChannelHandler initializer;

    @Value("${server.ip:127.0.0.1}")
    private String host;

    @Value("${server.port:8888}")
    private int port;

    private Channel channel;

    public void start() throws Exception {
        try {
            logger.info("[info] >>> start netty client.");
            bootstrap.group(workerGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(initializer);
            logger.info("[info] >>> set netty client params");
            bootstrap.option(ChannelOption.SO_BACKLOG, 65535);
            //keep connect
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            bootstrap.option(ChannelOption.TCP_NODELAY, true);


            channel = bootstrap.connect(host, port).syncUninterruptibly().channel();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[info] >>> netty client start fail.");
        }
    }

    public void destroy() {
        try {
            if (channel != null) {
                channel.close();
            }
            workerGroup.shutdownGracefully();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[info] >>> destroy netty server exception." + e.getMessage());
        }
    }

    private void checkConnect() {
        if (channel != null && channel.isActive())
            return;

        //fast reconnect
        while (true) {
            try {
                channel = bootstrap.connect(host, port).syncUninterruptibly().channel();

                if (channel == null || !channel.isActive()) {
                    Thread.sleep(10);
                    continue;
                }

                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(Object obj) {
        //check and reconnect
        checkConnect();

        //send message;
        send2Server(obj);
    }

    private void send2Server(Object obj) {
        channel.writeAndFlush(obj);
    }
}
