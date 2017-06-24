package org.dncf.handler;

import org.dncf.protocol.CommonDecoder;
import org.dncf.protocol.CommonEncoder;
import org.dncf.service.AccessService;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by LJT on 17-6-6.
 * email: ljt1343@gmail.com
 */
@Component
public class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

    @Autowired
    private AccessService accessService;

    @Value("${node.name:test}")
    private String name;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new CommonEncoder());
        socketChannel.pipeline().addLast(new CommonDecoder());

        socketChannel.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
        socketChannel.pipeline().addLast(new FSServiceHandler(accessService, name));
    }
}
