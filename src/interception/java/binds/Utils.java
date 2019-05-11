package interception.java.binds;

public class Utils {
    /**
     * Unite two bytes to one short.
     *
     * @param a Left part of short.
     * @param b Right part of short.
     * @return Short - composition of two bytes.
     */
    public static short bytesToShort(byte a, byte b) {
        return (short) (((int) a) | (((int) b) << 8));
    }

    /**
     * Unite two bytes to one char.
     *
     * @param a Left part of char.
     * @param b Right part of char.
     * @return Char - composition of two bytes.
     */
    public static char bytesToChar(byte a, byte b) {
        return (char) (((int) a) | (((int) b) << 8));
    }

    /**
     * Unite four bytes to one int.
     * <p>
     * Lets suppose that int is an array of 4 bytes.
     *
     * @param a [0] part of int.
     * @param b [1] part of int.
     * @param c [2] part of int.
     * @param d [3] part of int.
     * @return Integer - composition of four bytes.
     */
    public static int bytesToInt(byte a, byte b, byte c, byte d) {
        return ((((int) a) | (((int) b) << 8) | ((((int) c) << 16) | (((int) d) << 24))));
    }

    /**
     * Trying to simulate (Shift + c).
     *
     * @param c character.
     * @return {Shift + c} if possible or c if already (Shift + c). In other keys returns 0.
     */
    public static char toUpperCase(Character c) {
        if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z') {
            return Character.toUpperCase(c);
        } else {
            String low = "`1234567890-=[];',./\\";
            String up = "~!@#$%^&*()_+{}:\"<>?|";
            int pos = low.indexOf(c);
            if (pos != -1) {
                return up.charAt(pos);
            } else {
                if (up.indexOf(c) != -1 || "\n\t ".contains(c.toString())) {
                    return c;
                }
                System.err.println("ERROR");
                return 0;
            }
        }
    }
}
