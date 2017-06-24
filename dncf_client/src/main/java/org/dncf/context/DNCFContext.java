package org.dncf.context;

import org.dncf.core.TaskDispatcher;
import org.dncf.client.DNCNettyClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LJT on 17-6-6.
 * email: ljt1343@gmail.com
 */
public class DNCFContext {

    public static short MAGIC = 0x66;

    public static short VERSION = 1;

    public static Map<Integer, List<float[]>> CACHED_LIST = new HashMap<>();

    public static DNCNettyClient NETTY_CLIENT;

    public static TaskDispatcher TASK_DISPATCHER;
}
