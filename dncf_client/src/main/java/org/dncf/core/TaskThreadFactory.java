package org.dncf.core;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by LJT on 17-6-15.
 * email: ljt1343@gmail.com
 */
public class TaskThreadFactory implements ThreadFactory {

    private ThreadGroup group;

    protected String prefix = "TaskExecutor-";

    private AtomicInteger cnt = new AtomicInteger(0);

    public TaskThreadFactory() {
        SecurityManager s = System.getSecurityManager();
        group = s != null ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, prefix + cnt.addAndGet(1), 0);
        if (t.getPriority() != Thread.NORM_PRIORITY)
            t.setPriority(Thread.NORM_PRIORITY);
        return t;
    }
}
