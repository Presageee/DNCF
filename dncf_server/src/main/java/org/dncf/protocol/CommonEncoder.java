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
    protected void encode(ChannelHandlerContext channelHandlerContext, CommonProtocol fsProtocol, ByteBuf byteBuf) throws Exception {
        byteBuf.writeShort(fsProtocol.getMagic());
        byteBuf.writeShort(fsProtocol.getVersion());
        byteBuf.writeShort(fsProtocol.getType());
        if (fsProtocol.getType() != 1) {
            byteBuf.writeInt(fsProtocol.getLen());
            byteBuf.writeBytes(fsProtocol.getBody());
        }
    }
}
