package org.dncf.entity;

import java.util.List;

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
}
