package org.dncf.task;

import com.alibaba.fastjson.JSON;
import org.dncf.entity.ClientInfo;
import org.dncf.protocol.CommonProtocol;
import org.dncf.task.base.Task;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.UnknownHostException;

import static org.dncf.context.DNCFContext.*;

/**
 * Created by LJT on 17-6-6.
 * email: ljt1343@gmail.com
 */
@EnableScheduling
@Component
public class SendStatusTask implements Task {

    @Value("${node.name:test}")
    private String name;

    private void sendStatus() throws UnknownHostException {
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
    @Scheduled(cron = "0 0/3 * * * ?")
    public void execute() {
        try {
            sendStatus();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
