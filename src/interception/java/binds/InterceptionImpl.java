package interception.java.binds;

import com.sun.jna.*;
import interception.java.binds.key.binds.Keys;

import java.awt.*;
import java.util.function.Consumer;

import static interception.java.binds.InterceptionLibraryBinds.*;
import static interception.java.binds.Utils.toUpperCase;
import static interception.java.binds.key.binds.InterceptionFilterKeyState.*;
import static interception.java.binds.key.binds.InterceptionFilterMouseState.*;
import static interception.java.binds.key.binds.InterceptionKeyState.*;

public class InterceptionImpl implements Interception {

    private boolean debug = false;
    private int inputSleep = 1;
    private int mouseId = 12;
    private int keyboardId = 2;

    private Pointer context;
    private int awaitingTime = 100;
    private Thread thread;

    private Consumer<MouseStroke> mouseModifier = x -> {
    };

    private Consumer<KeyStroke> keyModifier = x -> {
    };

    private Consumer<UnknownStroke> other = x -> {
    };

    @Override
    public void enableDebug() {
        debug = true;
    }

    @Override
    public void disableDebug() {
        debug = false;
    }

    @Override
    public void setInputSleep(int val) {
        inputSleep = val;
    }

    @Override
    public void unload() {

        thread.interrupt();
        InterceptionLibraryBinds.interception_destroy_context(context);

    }

    @Override
    public void load() {
        context = interception_create_context();

        interception_set_filter(context, InterceptionLibraryBinds::interception_is_keyboard, (short) FILTER_MOUSE_ALL);
        interception_set_filter(context, InterceptionLibraryBinds::interception_is_mouse, (short) FILTER_KEY_ALL);

        thread = new Thread(new KeyHook());
        thread.setDaemon(true);
        thread.setPriority(10);
        thread.start();
        System.err.println("Loaded");
        if (setKeyboardId() == -1) {
            keyboardId = 2;
        }
        try {
            Thread.sleep(awaitingTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (setMouseId() == -1) {
            mouseId = 12;
        }
    }

    @Override
    public void resetMouseModifier(Consumer<MouseStroke> modifier) {
        mouseModifier = modifier;
    }

    @Override
    public void resetKeyModifier(Consumer<KeyStroke> modifier) {
        keyModifier = modifier;
    }

    @Override
    public void resetOtherModifier(Consumer<UnknownStroke> modifier) {
        other = modifier;
    }

    @Override
    public int setMouseId() {
        System.out.println("Press any mouse key to set mouseId.");
        mouseId = interception_wait(context);
        System.err.println(mouseId);
        if (interception_is_mouse(mouseId) > 0) {
            System.out.println("Mouse id set.");
        } else {
            System.out.println("It is not a mouse, try another device.");
            mouseId = -1;
        }
        return mouseId;
    }

    @Override
    public int setKeyboardId() {
        System.out.println("Press any keyboard key to set keyboardId.");

        keyboardId = interception_wait(context);

        System.err.println(keyboardId);
        if (interception_is_keyboard(keyboardId) > 0) {
            System.out.println("Keyboard id set.");
        } else {
            System.out.println("It is not a keyboard, try another device.");
            keyboardId = -1;
        }
        return keyboardId;
    }

    @Override
    public void pressLeftMouseButton() {
        MouseStroke mouse = new MouseStroke();
        mouse.state = 1;
        interception_send(context, mouseId, mouse.toBytes(), 1);
    }

    @Override
    public void releaseLeftMouseButton() {
        MouseStroke mouse = new MouseStroke();
        mouse.state = 2;
        interception_send(context, mouseId, mouse.toBytes(), 1);
    }

    @Override
    public void pressKey(char key) {
        KeyStroke keyStroke = new KeyStroke();
        keyStroke.code = key;
        keyStroke.state = KEY_DOWN;
        interception_send(context, keyboardId, keyStroke.toBytes(), 1);
    }

    @Override
    public void releaseKey(char key) {
        KeyStroke keyStroke = new KeyStroke();
        keyStroke.code = key;
        keyStroke.state = KEY_UP;
        interception_send(context, keyboardId, keyStroke.toBytes(), 1);
    }

    @Override
    public void moveMouseOn(int x, int y) {
        MouseStroke mouse = new MouseStroke();
        mouse.x = x;
        mouse.y = y;
        byte[] bytes = mouse.toBytes();
        interception_send(context, mouseId, bytes, 1);
    }

    @Override
    public void moveMouseTo(int x, int y) {
        Point p = MouseInfo.getPointerInfo().getLocation();
        int deltaX = x - p.x;
        int deltaY = y - p.y;
        moveMouseOn(deltaX, deltaY);
    }

    @Override
    public void writeChar(Character c) {
        boolean upprer = false;
        if (c >= 'A' && c <= 'Z' || "~!@#$%^&*()_+{}:\"<>?|".contains(c.toString())) {
            upprer = true;
        }
        c = toUpperCase(c);
        String chars = "QWERTYUIOPASDFGHJKLZXCVBNM \n\t~!@#$%^&*()_+{}:\"<>?| \n\t";
        int pos = chars.indexOf(c);
        if (pos == -1) {
            System.err.println("ERROR");
            return;
        }
        Character[] keys = {
                Keys.Q, Keys.W, Keys.E, Keys.R, Keys.T, Keys.Y, Keys.U, Keys.I, Keys.O, Keys.P
                , Keys.A, Keys.S, Keys.D, Keys.F, Keys.G, Keys.H, Keys.J, Keys.K, Keys.L
                , Keys.Z, Keys.X, Keys.C, Keys.V, Keys.B, Keys.N, Keys.M
                , Keys.Space, Keys.Enter, Keys.Tab
                , Keys.Tilde, Keys.One, Keys.Two, Keys.Three, Keys.Four, Keys.Five, Keys.Six, Keys.Seven, Keys.Eight, Keys.Nine, Keys.Zero
                , Keys.DashUnderscore, Keys.PlusEquals, Keys.OpenBracketBrace, Keys.CloseBracketBrace, Keys.SemicolonColon, Keys.SingleDoubleQuote
                , Keys.CommaLeftArrow, Keys.PeriodRightArrow, Keys.ForwardSlashQuestionMark, Keys.BackslashPipe
                , Keys.Space, Keys.Enter, Keys.Tab};
        if (upprer) {
            pressKey(Keys.LeftShift);
        }
        try {
            Thread.sleep(inputSleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pressKey(keys[chars.indexOf(c)]);
        try {
            Thread.sleep(inputSleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        releaseKey(keys[chars.indexOf(c)]);
        if (upprer) {
            releaseKey(Keys.LeftShift);
        }
    }

    @Override
    public void writeText(String s) {
        for (Character c : s.toCharArray()) {
            writeChar(c);
        }
    }

    private void hookerMouseOut(MouseStroke x) {
        System.err.println("===================================");
        System.err.println("Mouse id = " + mouseId);
        System.err.println("State = " + (short) x.state);
        System.err.println("Flags = " + (short) x.flags);
        System.err.println("X = " + x.x);
        System.err.println("Y = " + x.y);
        System.err.println("Rolling = " + x.rolling);
        System.err.println("===================================");
    }

    private void hookerKeyOut(KeyStroke x) {
        System.err.println("===================================");
        System.err.println("Keyboard id = " + mouseId);
        System.err.println("State = " + (short) x.state);
        System.err.println("Code = " + (short) x.code);
        System.err.println("===================================");
    }

    private void hookerOtherOut(UnknownStroke x) {
        System.err.println("===================================");
        System.err.println("Unknown device id = " + x.id);
        System.err.println("===================================");
    }

    private class KeyHook implements Runnable {
        @Override
        public void run() {
            while (!Thread.interrupted()) {
                byte[] stroke = new byte[STROKE_SIZE];
                int current_id = interception_wait(context);
                if (current_id == keyboardId) {
                    if (interception_receive(context, keyboardId, stroke, 1) > 0) {
                        KeyStroke key = new KeyStroke(stroke);
                        keyModifier.accept(key);
                        if (debug) {
                            hookerKeyOut(key);
                        }
                        interception_send(context, keyboardId, key.toBytes(), 1);
                    }
                } else if (current_id == mouseId) {
                    if (interception_receive(context, mouseId, stroke, 1) > 0) {
                        MouseStroke mouse = new MouseStroke(stroke);
                        mouseModifier.accept(mouse);
                        if (debug) {
                            hookerMouseOut(mouse);
                        }
                        interception_send(context, mouseId, mouse.toBytes(), 1);
                    }
                } else if (interception_receive(context, current_id, stroke, 1) > 0) {
                    UnknownStroke st = new UnknownStroke(stroke, current_id);
                    other.accept(st);
                    if (debug) {
                        hookerOtherOut(st);
                    }
                    interception_send(context, current_id, stroke, 1);
                } else
                    break;
            }
            interception_destroy_context(context);
        }
    }
}
