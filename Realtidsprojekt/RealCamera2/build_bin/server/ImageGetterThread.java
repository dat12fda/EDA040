package server;

import se.lth.cs.eda040.realcamera.AxisM3006V;
import util.Logger;

public class ImageGetterThread extends Thread {
	private String TAG = ImageGetterThread.class.getSimpleName();
	private AxisM3006V camera;
	private ServerMonitor serverMonitor;
	private int sleepTime;
	
	public ImageGetterThread(AxisM3006V camera, ServerMonitor serverMonitor) {
		this.camera = camera;
		this.serverMonitor=serverMonitor;
		camera.init();

	}

	@Override
	public void run() {
		while (!isInterrupted()) {
			byte[] jpeg = new byte[AxisM3006V.IMAGE_BUFFER_SIZE];
			int len = camera.getJPEG(jpeg, 0);
			byte[] timeArray = new byte[AxisM3006V.TIME_ARRAY_SIZE];
			camera.getTime(timeArray, 0);
			boolean detected = camera.motionDetected();
			if (detected) {
				serverMonitor.setMotionDetected(detected);
				sleepTime = 40;
			} else if(!serverMonitor.getMotionDetected()) {
				sleepTime = 1000;
			}
			long timeValue = 0;
			for (int i = 0; i < timeArray.length; i++) {
				timeValue = (timeValue << 8) + (timeArray[i] & 0xff);
			}
			serverMonitor.put(new ImageWrapper(len, timeValue, detected, jpeg));
			//Logger.log(TAG, "Posted new image to monitor");
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

}
