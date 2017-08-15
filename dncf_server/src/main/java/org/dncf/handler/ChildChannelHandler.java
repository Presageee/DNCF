package org.dncf.handler;

import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import org.dncf.protocol.CommonDecoder;
import org.dncf.protocol.CommonEncoder;
import org.dncf.service.AccessService;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by LJT on 17-6-6.
 * email: ljt1343@gmail.com
 */
@Component
public class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

    @Autowired
    private AccessService accessService;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        static final EventExecutorGroup group = new DefaultEventExecutorGroup(Runtime.getRuntime().availableProcessors() + 1);

        socketChannel.pipeline().addLast(new CommonEncoder());
        socketChannel.pipeline().addLast(new CommonDecoder());

        socketChannel.pipeline().addLast(group, new CommonServiceHandler(accessService));
    }
}
