import interception.java.binds.Interception;
import interception.java.binds.InterceptionImpl;
import interception.java.binds.key.binds.Keys;

import java.util.concurrent.atomic.AtomicBoolean;


public class Main {

    public static void main(String[] args) {
        Interception interception = new InterceptionImpl();
        interception.load();

        interception.unload();
    }
}

/*
java.library.path
*/




















