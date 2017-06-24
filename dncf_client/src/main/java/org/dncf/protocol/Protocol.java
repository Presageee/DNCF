package org.dncf.protocol;

/**
 * Created by LJT on 17-6-6.
 * email: ljt1343@gmail.com
 */
public class Protocol {
    private short magic = 0x66;

    private short type; // 1 heart 2 client info 3 load memory 4 search

    private short version;
    public short getVersion() {
        return version;
    }

    public void setVersion(short version) {
        this.version = version;
    }

    public short getMagic() {
        return magic;
    }

    public void setMagic(short magic) {
        this.magic = magic;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }
}
