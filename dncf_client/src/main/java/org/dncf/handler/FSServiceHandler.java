package org.dncf.handler;

import com.alibaba.fastjson.JSON;
import org.dncf.context.DNCFContext;
import org.dncf.entity.ClientInfo;
import org.dncf.protocol.CommonProtocol;
import org.dncf.service.AccessService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.dncf.context.DNCFContext.*;

/**
 * Created by LJT on 17-6-6.
 * email: ljt1343@gmail.com
 */
public class FSServiceHandler extends ChannelInboundHandlerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(FSServiceHandler.class);

    private AccessService accessService;

    private String name;

    public FSServiceHandler(AccessService accessService, String name) {
        this.accessService = accessService;
        this.name = name;
    }



    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        CommonProtocol commonProtocol = new CommonProtocol();
        commonProtocol.setMagic(MAGIC);
        commonProtocol.setVersion(VERSION);
        commonProtocol.setType((short) 1);

        NETTY_CLIENT.send(commonProtocol);

        ClientInfo clientInfo = new ClientInfo();
        CommonProtocol data = new CommonProtocol();
        data.setMagic(MAGIC);
        data.setVersion(VERSION);
        data.setType((short) 2);
        clientInfo.build(name, CACHED_LIST);
        data.setBody(JSON.toJSONBytes(clientInfo));
        data.setLen(data.getBody().length);

        NETTY_CLIENT.send(data);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        CommonProtocol protocol = (CommonProtocol) msg;

        if (!validate(protocol)) {
            logger.error("[error] magic or version or body invalidate");
            return;
        }

        accessService.access(protocol, ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();

    }

    private boolean validate(CommonProtocol commonProtocol) {
        return commonProtocol.getMagic() == MAGIC && commonProtocol.getVersion() == DNCFContext.VERSION
                && commonProtocol.getLen() == commonProtocol.getBody().length;
    }

}
