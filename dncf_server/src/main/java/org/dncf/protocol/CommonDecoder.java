package org.dncf.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by LJT on 17-6-6.
 * email: ljt1343@gmail.com
 */
public class CommonDecoder extends ByteToMessageDecoder {
    private final static Logger logger = LoggerFactory.getLogger(CommonDecoder.class);

    private final static int BASE_LENGTH = 6;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list)
            throws Exception {
        if (byteBuf.readableBytes() < BASE_LENGTH) {
            logger.error("[error] >>> head length error");
            return;
        }

        byteBuf.markReaderIndex();

        CommonProtocol protocol = new CommonProtocol();
        protocol.setMagic(byteBuf.readShort());
        protocol.setVersion(byteBuf.readShort());
        protocol.setType(byteBuf.readShort());


        if(protocol.getType() != 1) {
            //read len error
            if (byteBuf.readableBytes() < 4) {
                byteBuf.resetReaderIndex();
                return;
            }
            protocol.setLen(byteBuf.readInt());

            //read body error
            if (byteBuf.readableBytes() < protocol.getLen()) {
                byteBuf.resetReaderIndex();
                return;
            }

            byte[] data = new byte[protocol.getLen()];


            byteBuf.readBytes(data);
            protocol.setBody(data);
        }
        list.add(protocol);
    }
}
