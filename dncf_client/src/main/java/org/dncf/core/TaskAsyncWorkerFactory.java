package org.dncf.core;

/**
 * Created by LJT on 17-6-15.
 * email: ljt1343@gmail.com
 */
public class TaskAsyncWorkerFactory extends TaskThreadFactory {

    public TaskAsyncWorkerFactory() {
        super();
        prefix = "TaskAsyncWorker-";
    }

}
