package org.dncf.task.base;

/**
 * Created by LJT on 2017/6/14.
 */
public class AsyncTask implements Runnable {

    private Task task;

    public AsyncTask(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        task.execute();
    }
}
