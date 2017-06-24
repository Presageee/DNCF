package org.dncf.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.net.InetSocketAddress;

/**
 * Created by LJT on 17-6-6.
 * email: ljt1343@gmail.com
 */
@Configuration
public class NettyConfiguration {

    @Value("${server.port:8888}")
    public int port;

    @Value("${is.linux:false}")
    public boolean isLinux;

    /**
     * socket网络地址
     *
     * @return
     */
    @Bean
    public InetSocketAddress getAddress() {
        return new InetSocketAddress(port);
    }

    /**
     * 服务端启动器。
     *
     * @return
     */
    @Bean(name = { "bootstrap" })
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Bootstrap bootstrap() {
        return new Bootstrap();
    }

    @Bean(name = "bossGroup")
    public EventLoopGroup getBossGroup() {
        if (isLinux) {
            return new EpollEventLoopGroup(0x1,
                    new NettyThreadFactory("@+DNBossThread", Thread.NORM_PRIORITY));
        } else {
            return new NioEventLoopGroup(0x1, new NettyThreadFactory("@+DNBossThread", Thread.NORM_PRIORITY));
        }
    }

    @Bean(name = "workerGroup")
    public EventLoopGroup getWorkerGroup() {
        if (isLinux) {
            return new EpollEventLoopGroup(Runtime.getRuntime().availableProcessors() + 0x1,
                    new NettyThreadFactory("@+DNWorkerThread", Thread.NORM_PRIORITY));
        } else {
            return new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() + 0x1,
                    new NettyThreadFactory("@+DNWorkerThread", Thread.NORM_PRIORITY));
        }
    }
}
