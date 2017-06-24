package org.dncf.protocol;

/**
 * Created by LJT on 17-6-6.
 * email: ljt1343@gmail.com
 */
public class CommonProtocol extends Protocol {
    private int len;

    private byte[] body;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
