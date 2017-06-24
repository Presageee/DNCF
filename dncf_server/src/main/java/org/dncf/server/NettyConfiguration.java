package org.dncf.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

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

    @Bean(name = { "serverBootstrap" })
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public ServerBootstrap serverBootstrap() {
        return new ServerBootstrap();
    }

    @Bean(name = "bossGroup")
    public EventLoopGroup getBossGroup() {
        if (isLinux) {
            return new EpollEventLoopGroup(0x1,
                    new NettyThreadFactory("@+DNCSBossThread", Thread.NORM_PRIORITY));
        } else {
            return new NioEventLoopGroup(0x1, new NettyThreadFactory("@+DNCSBossThread", Thread.NORM_PRIORITY));
        }
    }

    @Bean(name = "workerGroup")
    public EventLoopGroup getWorkerGroup() {
        if (isLinux) {
            return new EpollEventLoopGroup(Runtime.getRuntime().availableProcessors() + 0x1,
                    new NettyThreadFactory("@+DNCSWorkerThread", Thread.NORM_PRIORITY));
        } else {
            return new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() + 0x1,
                    new NettyThreadFactory("@+DNCSWorkerThread", Thread.NORM_PRIORITY));
        }
    }
}
