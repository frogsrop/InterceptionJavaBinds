package interception.java.binds;

public class UnknownStroke {
    public int id;
    public byte[] data;

    public UnknownStroke() {
        id = 0;
        data = null;
    }

    public UnknownStroke(byte[] bytes, int id) {
        id = id;
        data = bytes;
    }

    public byte[] toBytes() {
        return data;
    }
}
