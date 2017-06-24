package org.dncf.client;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by LJT on 17-6-6.
 * email: ljt1343@gmail.com
 */
public class NettyThreadFactory implements ThreadFactory {

    private int priority;
    private String name;
    private AtomicInteger threadNumber = new AtomicInteger(1);
    private ThreadGroup group;

    /**
     *
     *
     * @param name
     *
     * @param priority
     *
     */
    public NettyThreadFactory(String name, int priority) {
        this.priority = priority;
        this.name = name;
        this.group = new ThreadGroup(name);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(this.group, r);
        t.setName(this.name + "-" + "#-" + this.threadNumber.getAndIncrement());
        t.setPriority(this.priority);
        return t;
    }

    public ThreadGroup getGroup() {
        return group;
    }
}
