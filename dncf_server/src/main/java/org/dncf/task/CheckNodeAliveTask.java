package org.dncf.task;

import org.dncf.task.base.Task;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

import static org.dncf.context.DNCSContext.*;

/**
 * Created by LJT on 17-6-15.
 * email: ljt1343@gmail.com
 */
@Component
@EnableScheduling
public class CheckNodeAliveTask implements Task {

    private static final Logger logger = LoggerFactory.getLogger(CheckNodeAliveTask.class);

    @Value("${client.timeout:60000}")
    private int timeout;


    private void check() {
        long now = System.currentTimeMillis();

        for (Map.Entry<Channel, Long> entry :CLIENT_HEART_MAP.entrySet()) {
            long dis = now - entry.getValue();
            if (dis >= timeout) {
               removeChannel(entry.getKey());
               logger.info("[info] >>> node offline");
            }
        }
    }

    @Override
    @Scheduled(cron = "0/20 * * * * ?")
    public void execute() {
        check();
    }
}
