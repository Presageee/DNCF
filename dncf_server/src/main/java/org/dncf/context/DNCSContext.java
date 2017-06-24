package org.dncf.context;

import org.dncf.entity.ClientInfo;
import org.dncf.entity.CoreChannel;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by LJT on 17-6-6.
 * email: ljt1343@gmail.com
 */
public class DNCSContext {
    public static Map<String, Channel> CLIENT_CHANNEL_MAP = new HashMap<>();

    public static Map<String, ClientInfo> CLIENT_INFO_MAP = new HashMap<>();

    public static Map<Channel, Long> CLIENT_HEART_MAP = new HashMap<>();

    public static AtomicInteger CORE_TOTAL = new AtomicInteger(0);

    public static ConcurrentHashMap<String, List<float[]>> SEARCH_RESULT = new ConcurrentHashMap<>();

    public static List<CoreChannel> CORE_LIST = new ArrayList<>();

    public static AtomicInteger CLIENT_TOTAL = new AtomicInteger(0);

    public static short MAGIC = 0x66;

    public static short VERSION = 1;

    public static void removeChannel(Channel channel) {
        synchronized (DNCSContext.class) {
            int sum = 0;
            List<CoreChannel> coreChannels = new ArrayList<>();
            for (int i = 0; i < CORE_LIST.size(); i++) {
                if (CORE_LIST.get(i).getChannel() != channel) {
                    coreChannels.add(CORE_LIST.get(i));
                    sum ++;
                }
            }
            CORE_LIST = coreChannels;
            CORE_TOTAL.set(sum);


            CLIENT_TOTAL.decrementAndGet();


            StringBuilder sb = new StringBuilder();
            CLIENT_CHANNEL_MAP.forEach((e, v) -> {
                if (v == channel) sb.append(e);
            });
            String nodeName = sb.toString();
            CLIENT_INFO_MAP.remove(nodeName);
            CLIENT_CHANNEL_MAP.remove(nodeName);
            CLIENT_HEART_MAP.remove(channel);
        }
    }
}
