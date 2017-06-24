package org.dncf.core;

import org.dncf.task.base.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.dncf.context.DNCFContext.*;

/**
 * Created by LJT on 2017/6/14.
 */
@Component
public class TaskDispatcher {
    private static final Logger logger = LoggerFactory.getLogger(TaskDispatcher.class);

    private ConcurrentLinkedQueue<Task> taskQueue;

    private ExecutorService executorThread;

    private TaskExecutor taskExecutor;

    @PostConstruct
    public void init() {
        taskQueue = new ConcurrentLinkedQueue<>();

        taskExecutor = new TaskExecutor(taskQueue);

        executorThread = Executors.newSingleThreadExecutor(new TaskThreadFactory());
        executorThread.submit(taskExecutor);

        TASK_DISPATCHER = this;
    }


    public void putTask(Task task) {
        synchronized (taskQueue) {
            boolean succeed = taskQueue.add(task);
            if (succeed)
                taskQueue.notifyAll();
        }
    }

}
