package org.dncf.service.impl;

import com.alibaba.fastjson.JSON;
import org.dncf.protocol.Protocol;
import org.dncf.service.AccessService;
import org.dncf.service.BaseService;
import org.dncf.service.InfoService;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by LJT on 17-6-6.
 * email: ljt1343@gmail.com
 */
@Service
public class AccessServiceImpl implements AccessService {

    private static final Logger logger = LoggerFactory.getLogger(AccessService.class);

    @Autowired
    private InfoService infoService;

    @Autowired
    private BaseService baseService;

    @Override
    public void access(Protocol protocol) {

    }

    @Override
    public void access(Protocol protocol, Channel channel) {
        switch (protocol.getType()) {
            case 1:
                infoService.updateHeartTime(channel);
                return;
            case 2:
                infoService.saveClientInfo(protocol, channel);
                return;
            case 3:
                baseService.access(protocol, channel);
                return;
            default:
                logger.error("[error] >>> error type:\n{}", JSON.toJSONString(protocol));
        }
    }
}
