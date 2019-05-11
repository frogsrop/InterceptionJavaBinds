package interception.java.binds;

import static interception.java.binds.InterceptionLibraryBinds.STROKE_SIZE;
import static interception.java.binds.Utils.bytesToChar;

public class KeyStroke {
    public char code;
    public char state;

    public KeyStroke() {
        code = 0;
        state = 0;
    }

    public KeyStroke(byte[] bytes) {
        code = bytesToChar(bytes[0], bytes[1]);
        state = bytesToChar(bytes[2], bytes[3]);
    }

    public byte[] toBytes() {
        byte[] data = new byte[STROKE_SIZE];
        data[0] = (byte) (code & 0xff);
        data[1] = (byte) ((code >> 8) & 0xff);
        data[2] = (byte) (state & 0xff);
        data[3] = (byte) ((state >> 8) & 0xff);
        return data;
    }

}
