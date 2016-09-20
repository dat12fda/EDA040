package util;

public class Logger {
    private static int level = 0;

    public synchronized static void info(String TAG, String msg) {
        if (level >= 2) {
            log("INFO", TAG, msg);
        }
    }

    public synchronized static void error(String TAG, String msg) {
        if (level >= 1) {
            log("ERROR", TAG, msg);
        }
    }

    private synchronized static void log(String type, String source, String msg) {
        System.out.format("%-10s%-25s%1s", type, source + ": ", msg);
        System.out.println();
    }

    public synchronized static void setLoggingLevel(int newLevel) {
        level = newLevel;
    }
}
