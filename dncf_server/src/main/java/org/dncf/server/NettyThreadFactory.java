package org.dncf.server;

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
     * 设置线程相关属性
     *
     * @param name
     *            线程池名
     * @param priority
     *            线程池优先级
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
