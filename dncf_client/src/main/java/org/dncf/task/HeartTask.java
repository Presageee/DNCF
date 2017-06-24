package org.dncf.task;

import org.dncf.protocol.CommonProtocol;
import org.dncf.task.base.Task;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static org.dncf.context.DNCFContext.MAGIC;
import static org.dncf.context.DNCFContext.NETTY_CLIENT;
import static org.dncf.context.DNCFContext.VERSION;

/**
 * Created by LJT on 17-6-15.
 * email: ljt1343@gmail.com
 */
@Component
@EnableScheduling
public class HeartTask implements Task {

    public void heart() {
        CommonProtocol commonProtocol = new CommonProtocol();
        commonProtocol.setMagic(MAGIC);
        commonProtocol.setVersion(VERSION);
        commonProtocol.setType((short) 1);

        NETTY_CLIENT.send(commonProtocol);
    }

    @Override
    @Scheduled(cron = "0/10 * * * * ?")
    public void execute() {
        heart();
    }
}
