package org.dncf.core;

import org.dncf.task.base.AsyncTask;
import org.dncf.task.base.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by LJT on 2017/6/14.
 */
public class TaskExecutor implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(TaskExecutor.class);

    private ConcurrentLinkedQueue<Task> taskQueue;

    private ExecutorService asyncExecutor;

    private int poolSize = Runtime.getRuntime().availableProcessors() + 1;

    public TaskExecutor() {}

    public TaskExecutor(ConcurrentLinkedQueue<Task> taskQueue) {
        this.taskQueue = taskQueue;

        asyncExecutor = Executors.newFixedThreadPool(poolSize, new TaskAsyncWorkerFactory());
    }



    @Override
    public void run() {
        while (true) {
            try {
                Task task;

                synchronized (taskQueue) {
                    task = taskQueue.poll();
                    if (task == null) {
                        taskQueue.wait();
                        continue;
                    }
                }

                if (task.isAsync()) {
                    asyncExecutor.submit(new AsyncTask(task));
                    continue;
                }

                task.execute();
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("[error] >>> dispatcher task error: {}", e.getMessage());
            }
        }
    }
}
