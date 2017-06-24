package org.dncf.service.impl;

import com.alibaba.fastjson.JSON;
import org.dncf.context.DNCSContext;
import org.dncf.entity.ClientInfo;
import org.dncf.entity.CoreChannel;
import org.dncf.protocol.CommonProtocol;
import org.dncf.protocol.Protocol;
import org.dncf.service.InfoService;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LJT on 17-6-6.
 * email: ljt1343@gmail.com
 */
@Service
public class InfoServiceImpl implements InfoService {
    private final static Logger logger = LoggerFactory.getLogger(InfoService.class);

    @Override
    public boolean saveClientInfo(Protocol protocol, Channel channel) {
        if (!(protocol instanceof CommonProtocol)) {
            logger.error("[error] >>> protocol is not correctly");
            return false;
        }


        String clientJson = new String(((CommonProtocol) protocol).getBody());
        ClientInfo info = JSON.parseObject(clientJson, ClientInfo.class);

        logger.info("[info] >>> current sync client info: \n{}", JSON.toJSONString(info, true));

        boolean isCache = false;

        if (!(isCache = DNCSContext.CLIENT_INFO_MAP.containsKey(info.getNodeName()))) {
            synchronized (DNCSContext.class) {
                for (int i = 0; i < info.getNodeCore(); i++) {
                    CoreChannel coreChannel = new CoreChannel();
                    coreChannel.setChannel(channel);
                    coreChannel.setNodeName(info.getNodeName());
                    DNCSContext.CORE_LIST.add(coreChannel);
                }
            }

            logger.info("[info] >>> client total count: {}", DNCSContext.CLIENT_TOTAL.incrementAndGet());
            logger.info("[info] >>> core total count: {}", DNCSContext.CORE_TOTAL.addAndGet(info.getNodeCore()));
        }

        DNCSContext.CLIENT_INFO_MAP.put(info.getNodeName(), info);
        DNCSContext.CLIENT_CHANNEL_MAP.put(info.getNodeName(), channel);

        if (isCache) {
            logger.info("[info] >>> client total count: {}", DNCSContext.CLIENT_TOTAL.get());
            logger.info("[info] >>> core total count: {}", DNCSContext.CORE_TOTAL.get());
        }

        return true;
    }

    @Override
    public void updateHeartTime(Channel channel) {
        if (null != channel) {
            DNCSContext.CLIENT_HEART_MAP.put(channel, System.currentTimeMillis());
            logger.info("[info] >>> {} heart", channel.toString());
        }
    }

    @Override
    public boolean removeClientInfo(Protocol protocol) {
        if (!(protocol instanceof CommonProtocol)) {
            logger.error("[error] >>> protocol is not correctly");
            return false;
        }


        String clientJson = new String(((CommonProtocol) protocol).getBody());
        ClientInfo info = JSON.parseObject(clientJson, ClientInfo.class);


        synchronized (DNCSContext.class) {
            int sum = 0;
            List<CoreChannel> coreChannels = new ArrayList<>();
            for (int i = 0; i < DNCSContext.CORE_LIST.size(); i++) {
                if (!DNCSContext.CORE_LIST.get(i).getNodeName().equals(info.getNodeName())) {
                    coreChannels.add(DNCSContext.CORE_LIST.get(i));
                    sum ++;
                }
            }
            DNCSContext.CORE_LIST = coreChannels;
            DNCSContext.CORE_TOTAL.set(sum);
        }

        DNCSContext.CLIENT_TOTAL.decrementAndGet();
        DNCSContext.CLIENT_INFO_MAP.remove(info.getNodeName());
        DNCSContext.CLIENT_CHANNEL_MAP.remove(info.getNodeName());
        logger.warn("[warn] >>> node {} offline", info.getNodeName());

        return true;
    }
}
