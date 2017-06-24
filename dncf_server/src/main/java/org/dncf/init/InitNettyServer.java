package org.dncf.init;

import org.dncf.server.DNCNettyServer;

/**
 * Created by LJT on 2017/6/13.
 */
public class InitNettyServer implements Runnable {

    private DNCNettyServer server;

    public InitNettyServer(DNCNettyServer server) {
        this.server = server;
    }


    @Override
    public void run() {
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
