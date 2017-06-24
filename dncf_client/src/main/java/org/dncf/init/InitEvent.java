package org.dncf.init;

import org.dncf.client.DNCNettyClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import static org.dncf.context.DNCFContext.NETTY_CLIENT;

/**
 * Created by LJT on 17-6-15.
 * email: ljt1343@gmail.com
 */
@Component
public class InitEvent implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private DNCNettyClient client;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

        try {
            NETTY_CLIENT = client;
            client.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
