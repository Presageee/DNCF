package org.dncf.handler;

import org.dncf.context.DNCSContext;
import org.dncf.protocol.CommonProtocol;
import org.dncf.service.AccessService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by LJT on 17-6-6.
 * email: ljt1343@gmail.com
 */
public class CommonServiceHandler extends ChannelInboundHandlerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(CommonServiceHandler.class);

    private AccessService accessService;

    public CommonServiceHandler(AccessService accessService) {
        this.accessService = accessService;
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

    private boolean validate(CommonProtocol fsProtocol) {
        boolean res = fsProtocol.getMagic() == DNCSContext.MAGIC && fsProtocol.getVersion() == DNCSContext.VERSION;
        if (fsProtocol.getBody() != null) {
            res = res && fsProtocol.getLen() == fsProtocol.getBody().length;
        }
        return res;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("[info] >>> channel {} offline", ctx.channel());
        Channel channel = ctx.channel();
        DNCSContext.removeChannel(channel);
    }
}
