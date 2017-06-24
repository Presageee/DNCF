package org.dncf.entity;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by LJT on 17-6-6.
 * email: ljt1343@gmail.com
 */
public class ClientInfo {
    private String nodeName;

    private String nodeIP;

    private Integer nodeCore;

    private Integer nodeMemory;

    private String nodePersistence;

    private List<DataStatus> coreStatuses;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeIP() {
        return nodeIP;
    }

    public void setNodeIP(String nodeIP) {
        this.nodeIP = nodeIP;
    }

    public Integer getNodeCore() {
        return nodeCore;
    }

    public void setNodeCore(Integer nodeCore) {
        this.nodeCore = nodeCore;
    }

    public Integer getNodeMemory() {
        return nodeMemory;
    }

    public void setNodeMemory(Integer nodeMemory) {
        this.nodeMemory = nodeMemory;
    }

    public String getNodePersistence() {
        return nodePersistence;
    }

    public void setNodePersistence(String nodePersistence) {
        this.nodePersistence = nodePersistence;
    }

    public List<DataStatus> getCoreStatuses() {
        return coreStatuses;
    }

    public void setCoreStatuses(List<DataStatus> coreStatuses) {
        this.coreStatuses = coreStatuses;
    }

    public void build(String name, Map<Integer, List<float[]>> CachedList) throws UnknownHostException {
        com.sun.management.OperatingSystemMXBean operatingSystemMXBean =  (com.sun.management.OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
        long size = operatingSystemMXBean.getTotalPhysicalMemorySize() / 1024 / 1024 / 1024;
        this.nodeMemory = (int)size;
        nodeCore= Runtime.getRuntime().availableProcessors();
        nodeIP = InetAddress.getLocalHost().getHostAddress();
        nodeName = name;
        coreStatuses = new ArrayList<>();
        CachedList.forEach((e, v) -> {
            DataStatus status = new DataStatus();
            status.setCoreId(e);
            status.setSize(v.size());
            coreStatuses.add(status);
        });
    }
}
