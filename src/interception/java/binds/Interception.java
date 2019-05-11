package interception.java.binds;

import java.util.function.Consumer;

public interface Interception {

    /**
     * Loads library.
     * <p>
     * Must be called before library use.
     */
    void load();

    /**
     * Unloads library.
     * <p>
     * Finishes all processes and stops library.
     */
    void unload();

    /**
     * Enables debug mode.
     * <p>
     * With debug option on, you will see such parameters as device id, mouse coordinates, key code and all states.
     * <p>
     * Note: Modifications goes before debug info output.
     */
    void enableDebug();

    /**
     * Disables debug mode.
     * <p>
     * With debug option off, you will not see any additional information.
     */
    void disableDebug();

    /**
     * Sets sleep between keyDown and keyUp.
     * <p>
     * This parameter affects only text input functions like {@link Interception#writeChar(Character)} or {@link Interception#writeText(String)}.
     *
     * @param sleep must be more that zero, in other keys the order of key input is unpredictable.
     * @see Interception#writeChar(Character)
     * @see Interception#writeText(String)
     */
    void setInputSleep(int sleep);

    /**
     * Sets mouse modifier.
     * <p>
     * Before mouse event will be sent it will be modified with modifier.
     *
     * @param modifier Modifier for mouse stroke.
     * @see MouseStroke
     */
    void resetMouseModifier(Consumer<MouseStroke> modifier);

    /**
     * Sets keyboard modifier.
     * <p>
     * Before key event will be sent it will be modified with modifier.
     *
     * @param modifier Modifier for key stroke.
     * @see KeyStroke
     */
    void resetKeyModifier(Consumer<KeyStroke> modifier);

    /**
     * Sets modifier for unknown devices.
     * <p>
     * Before any unknown device event will be sent it will be modified with modifier.
     *
     * @param modifier Modifier for unknown device.
     * @see UnknownStroke
     */
    void resetOtherModifier(Consumer<UnknownStroke> modifier);

    /**
     * Sets mouse id by receiving a mouse stroke.
     * <p>
     * Waits for a key stroke. If stroke received from mouse device sets mouse id.
     *
     * @return MouseId if set was successful and -1 in other case.
     */
    int setMouseId();

    /**
     * Sets keyboard id by receiving a key stroke.
     * <p>
     * Waits for a key stroke. If stroke received from keyboard device sets keyboard id.
     *
     * @return KeyboardId if set was successful and -1 in other case.
     */
    int setKeyboardId();

    /**
     * Sets left mouse button to pressed state.
     */
    void pressLeftMouseButton();

    /**
     * Sets left mouse button to released state.
     */
    void releaseLeftMouseButton();

    /**
     * Sets key button to pressed state.
     * <p>
     * Note: Be careful with this function because chars like 'a'-'z', '\', '+' etc. are not related to keys. Use instead {@link interception.java.binds.key.binds.Keys}
     *
     * @param key Keyboard key id.
     * @see interception.java.binds.key.binds.Keys
     */
    void pressKey(char key);

    /**
     * Sets key button to released state.
     * <p>
     * Note: Be careful with this function because chars like 'a'-'z', '\', '+' etc. are not related to keys. Use instead {@link interception.java.binds.key.binds.Keys}
     *
     * @param key Keyboard key id.
     * @see interception.java.binds.key.binds.Keys
     */
    void releaseKey(char key);

    /**
     * Move mouse on x, y coordinates.
     * <p>
     * Note: Before using this function be sure that you turned off mouse acceleration. In other case behavior is unpredictable.
     *
     * @param x X coordinate to move on.
     * @param y Y coordinate to move on.
     */
    void moveMouseOn(int x, int y);

    /**
     * Move mouse to x, y coordinates.
     * <p>
     * Note: Before using this function be sure that you turned off mouse acceleration. In other case behavior is unpredictable.
     *
     * @param x X coordinate to move to.
     * @param y Y coordinate to move to.
     */
    void moveMouseTo(int x, int y);

    /**
     * Writes character.
     * <p>
     * Simulates key presses of characters on keyboard.
     *
     * @param character Character to write.
     */
    void writeChar(Character character);

    /**
     * Writes text.
     * <p>
     * Simulates key presses of characters in text.
     *
     * @param text Text to write.
     */
    void writeText(String text);
}

