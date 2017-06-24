package org.dncf.service;

import org.dncf.protocol.Protocol;
import io.netty.channel.Channel;

/**
 * Created by LJT on 17-6-6.
 * email: ljt1343@gmail.com
 */
public interface AccessService {
    void access(Protocol protocol);

    void access(Protocol protocol, Channel channel);
}
