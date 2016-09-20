package util;

public class Logger {
    private static boolean on;

    public synchronized static void log(String TAG, String msg) {
        if (on) {
            System.out.format("%-25s%1s", TAG + ": ", msg);
            System.out.println();
        }
    }

    public synchronized static void setLoggingOn(boolean state) {
        on = state;
    }
}
