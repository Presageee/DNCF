package org.dncf.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by LJT on 17-6-6.
 * email: ljt1343@gmail.com
 */
public class CommonEncoder extends MessageToByteEncoder<CommonProtocol> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, CommonProtocol commonProtocol, ByteBuf byteBuf) throws Exception {
        byteBuf.writeShort(commonProtocol.getMagic());
        byteBuf.writeShort(commonProtocol.getVersion());
        byteBuf.writeShort(commonProtocol.getType());
        if (commonProtocol.getType() != 1) {
            byteBuf.writeInt(commonProtocol.getLen());
            byteBuf.writeBytes(commonProtocol.getBody());
        }
    }
}
