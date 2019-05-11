package interception.java.binds.key.binds;

import static interception.java.binds.key.binds.InterceptionKeyState.*;

public class InterceptionFilterKeyState {
    static int FILTER_KEY_NONE = 0x0000,
            FILTER_KEY_ALL = 0xFFFF,
            FILTER_KEY_DOWN = KEY_UP,
            FILTER_KEY_UP = KEY_UP << 1,
            FILTER_KEY_E0 = KEY_E0 << 1,
            FILTER_KEY_E1 = KEY_E1 << 1,
            FILTER_KEY_TERMSRV_SET_LED = KEY_TERMSRV_SET_LED << 1,
            FILTER_KEY_TERMSRV_SHADOW = KEY_TERMSRV_SHADOW << 1,
            FILTER_KEY_TERMSRV_VKPACKET = KEY_TERMSRV_VKPACKET << 1;
}
