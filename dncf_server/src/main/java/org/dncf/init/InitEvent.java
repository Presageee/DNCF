package org.dncf.init;

import org.dncf.server.DNCNettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by LJT on 17-6-15.
 * email: ljt1343@gmail.com
 */
@Component
public class InitEvent implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private DNCNettyServer fsNettyServer;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.submit(new InitNettyServer(fsNettyServer));
    }
}
