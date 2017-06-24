package org.dncf.entity;

import java.util.List;

/**
 * Created by LJT on 17-6-6.
 * email: ljt1343@gmail.com
 */
public class SearchResult {
    private String requestId;

    private List<Float> data;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public List<Float> getData() {
        return data;
    }

    public void setData(List<Float> data) {
        this.data = data;
    }
}
