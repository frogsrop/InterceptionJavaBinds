package interception.java.binds;

import com.sun.jna.Callback;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public class InterceptionLibraryBinds {

    static {
        String path = System.getProperty("java.library.path");
        Native.register(path + "\\interception.dll");
    }

    interface InterceptionPredicate extends Callback {
        int invoke(int device);
    }

    final static int STROKE_SIZE = (16 + 16 + 16 + 32 + 32 + 32) / 8;

    /**
     * Checks if device is keyboard.
     *
     * @param device device id.
     * @return 0 - if false and 1 - if true
     */
    public static native int interception_is_keyboard(int device);

    /**
     * Checks if device is mouse.
     *
     * @param device device id.
     * @return 0 - if false and 1 - if true
     */
    public static native int interception_is_mouse(int device);

    /**
     * Receive a stroke from all input devices.
     *
     * @param context Current context.
     * @param device  Device id.
     * @param stroke  Stroke info
     * @param nstroke Amount of strokes
     * @return 0 - if false and 1 - if true
     */
    public static native int interception_receive(Pointer context, int device, byte[] stroke, int nstroke);

    /**
     * Waiting for stroke in context.
     *
     * @param context Current context.
     * @return device id
     */
    public static native int interception_wait(Pointer context);

    /**
     * Waiting for stroke in context with timeout.
     *
     * @param context      Current context.
     * @param milliseconds Timeout.
     * @return device id or 0 if timeout
     */
    public static native int interception_wait_with_timeout(Pointer context, long milliseconds);

    /**
     * Sets filter for strokes.
     *
     * @param context   Current context.
     * @param predicate Device predicate.
     * @param filter    Strokes filter.
     */
    public static native void interception_set_filter(Pointer context, InterceptionPredicate predicate, short filter);

    /**
     * Sends input to OS.
     *
     * @param context Current context.
     * @param device  Device id.
     * @param stroke  Stroke info.
     * @param nstroke Amount of strokes.
     * @return ?
     */
    public static native int interception_send(Pointer context, int device, final byte[] stroke, int nstroke);

    /**
     * Creates context for interceptor.
     *
     * @return Context
     */
    public static native Pointer interception_create_context();

    /**
     * Destroys context for interceptor.
     * @param context Context to destroy.
     */
    public static native void interception_destroy_context(Pointer context);
}
