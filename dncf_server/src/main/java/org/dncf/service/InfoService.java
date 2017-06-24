package org.dncf.service;

import org.dncf.protocol.Protocol;
import io.netty.channel.Channel;

/**
 * Created by LJT on 17-6-6.
 * email: ljt1343@gmail.com
 */
public interface InfoService {
    boolean saveClientInfo(Protocol protocol, Channel channel);

    void updateHeartTime(Channel channel);

    boolean removeClientInfo(Protocol protocol);
}
