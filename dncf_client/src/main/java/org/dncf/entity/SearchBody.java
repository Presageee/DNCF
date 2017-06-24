package org.dncf.entity;

/**
 * Created by LJT on 17-6-6.
 * email: ljt1343@gmail.com
 */
public class SearchBody {
    private String requestId;

    private float[] data;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public float[] getData() {
        return data;
    }

    public void setData(float[] data) {
        this.data = data;
    }
}
