import interception.java.binds.Interception;
import interception.java.binds.InterceptionImpl;


public class Main {

    public static void main(String[] args) {
        Interception interception = new InterceptionImpl();
        interception.load();
        System.err.println("Waiting...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.err.println("Ready.");


        interception.enableDebug();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        interception.unload();
    }
}

/*
java.library.path
*/




















