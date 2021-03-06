package server;

import util.Logger;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ServerInputThread extends Thread {
    private static final String TAG = ServerInputThread.class.getSimpleName();
    private ServerMonitor serverMonitor;
    private DataInputStream is;

    public ServerInputThread(ServerMonitor serverMonitor, InputStream is) {
        this.serverMonitor = serverMonitor;
        this.is = new DataInputStream(is);
    }

    public void run() {
        while (!isInterrupted()) {
            try {
                boolean motionDetected = is.readBoolean();
                serverMonitor.setMotionDetected(motionDetected);
            } catch (IOException e) {
                Logger.error(TAG, "Connection probably closed. Message: " + e.getMessage());
                serverMonitor.closeSocket();
                return;
            }
        }
    }

}
