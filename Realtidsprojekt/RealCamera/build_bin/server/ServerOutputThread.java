package server;

import util.Logger;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ServerOutputThread extends Thread {

    private String TAG = ServerOutputThread.class.getSimpleName();
    private DataOutputStream os;
    private ServerMonitor serverMonitor;

    public ServerOutputThread(ServerMonitor serverMonitor, OutputStream os) {
        this.serverMonitor = serverMonitor;
        this.os = new DataOutputStream(os);
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                ImageWrapper imageWrapper = serverMonitor.get();

                //Logger.info(TAG, "Fetched new image from monitor");


                //Logger.info(TAG, "Writing image to outputstream");
                os.writeLong(imageWrapper.timeStamp);
                os.writeBoolean(imageWrapper.motionDetected);
                os.writeInt(imageWrapper.size);
                os.write(imageWrapper.image, 0, imageWrapper.size);
                //Logger.info(TAG, "Writing to outputstream complete");

            } catch (IOException e) {
                Logger.error(TAG, "Failed to write to outputstream. Connection probably closed. Message: " + e.getMessage());
                serverMonitor.closeSocket();
                return;
            } catch (InterruptedException e) {
                Logger.error(TAG, "Interrupted, connection probably closed. Message: " + e.getMessage());
            }
        }
    }

}
