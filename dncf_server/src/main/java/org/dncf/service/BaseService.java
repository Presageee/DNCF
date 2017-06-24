package org.dncf.service;

import io.netty.channel.Channel;
import org.dncf.protocol.Protocol;

/**
 * Created by ljt13 on 2017/6/24.
 */
public interface BaseService {
    void access(Protocol protocol);

    void access(Protocol protocol, Channel channel);
}
