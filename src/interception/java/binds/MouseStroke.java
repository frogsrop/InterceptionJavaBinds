package interception.java.binds;

import static interception.java.binds.InterceptionLibraryBinds.STROKE_SIZE;
import static interception.java.binds.Utils.*;

public class MouseStroke {
    public char state;
    public char flags;
    public short rolling;
    public int x;
    public int y;

    public MouseStroke() {
        state = 0;
        flags = 0;
        rolling = 0;
        x = 0;
        y = 0;
    }

    public MouseStroke(byte[] bytes) {
        state = bytesToChar(bytes[0], bytes[1]);
        flags = bytesToChar(bytes[2], bytes[3]);
        rolling = bytesToShort(bytes[4], bytes[5]);
        x = bytesToInt(bytes[8], bytes[9], bytes[10], bytes[11]);
        y = bytesToInt(bytes[12], bytes[13], bytes[14], bytes[15]);
    }

    public byte[] toBytes() {
        byte[] data = new byte[STROKE_SIZE];
        data[0] = (byte) (state & 0xff);
        data[1] = (byte) ((state >> 8) & 0xff);
        data[2] = (byte) (flags & 0xff);
        data[3] = (byte) ((flags >> 8) & 0xff);
        data[4] = (byte) (rolling & 0xff);
        data[5] = (byte) ((rolling >> 8) & 0xff);
        data[8] = (byte) (x & 0xff);
        data[9] = (byte) ((x >> 8) & 0xff);
        data[10] = (byte) ((x >> 16) & 0xff);
        data[11] = (byte) ((x >> 24) & 0xff);
        data[12] = (byte) (y & 0xff);
        data[13] = (byte) ((y >> 8) & 0xff);
        data[14] = (byte) ((y >> 16) & 0xff);
        data[15] = (byte) ((y >> 24) & 0xff);
        return data;
    }
}
