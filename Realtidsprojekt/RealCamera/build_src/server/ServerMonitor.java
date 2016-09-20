package server;

public class ServerMonitor {

    private boolean motionDetected;

    private ImageWrapper imageWrapper;
    private boolean hasImage;

    private boolean connected = false;

    public ServerMonitor() {

        hasImage = false;

    }

    public synchronized void put(ImageWrapper imageWrapper) {
        while (hasImage)
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        this.imageWrapper = imageWrapper.makeClone();
        hasImage = true;
        notifyAll();
    }

    public synchronized ImageWrapper get() throws InterruptedException {
        while (!hasImage)
            wait();
        hasImage = false;
        notifyAll();
        return imageWrapper.makeClone();
    }

    public synchronized void waitForSocketClose() {
        while (connected) {
            try {
                //Logger.info(TAG,"Socket is open");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public synchronized void openSocket() {
        connected = true;
    }

    public synchronized void closeSocket() {
        connected = false;
        notifyAll();
    }

    public synchronized boolean getMotionDetected() {
        return motionDetected;
    }

//	public synchronized void closeSocket() {
//		if(!socket.isClosed())
//		try {
//			socket.close();
//			notifyAll();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

    public synchronized void setMotionDetected(boolean motionDetected) {
        this.motionDetected = motionDetected;
    }

    public synchronized ImageWrapper httpGetJPEG() {
        while (imageWrapper == null)
            try {
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return imageWrapper.makeClone();
    }

}
