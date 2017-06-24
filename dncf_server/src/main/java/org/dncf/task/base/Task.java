package org.dncf.task.base;

import java.net.UnknownHostException;

/**
 * Created by LJT on 2017/6/14.
 */
public interface Task {
    default boolean isAsync() {
        return false;
    }

    void execute();
}
