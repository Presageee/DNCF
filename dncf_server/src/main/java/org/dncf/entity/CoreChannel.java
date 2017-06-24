package org.dncf.entity;

import io.netty.channel.Channel;

/**
 * Created by LJT on 17-6-6.
 * email: ljt1343@gmail.com
 */
public class CoreChannel {
    private String nodeName;

    private Channel channel;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
