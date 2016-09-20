

import se.lth.cs.eda040.realcamera.AxisM3006V;
import util.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import server.*;

public class Server {

	public static void main(String[] args) {

		int port = 6076;
		try {
			for (int i = 0; i < args.length; i++) {
				if (args[i].equals("-port")) {
					port = Integer.parseInt(args[i + 1]);
					i++;
				} else if (args[i].equals("-debug")) {
					Logger.setLoggingOn(Boolean.parseBoolean(args[i + 1]));
					i++;
				}
			}
			
			
			
			ServerSocket serverSocket = new ServerSocket(port);
			while (true) {
				Logger.log("ServerMain", "Waiting for connection on port " + port + ".");
				Socket clientSocket = serverSocket.accept();
				Logger.log("ServerMain", "New connection established");

				ServerMonitor serverMonitor = new ServerMonitor();
				serverMonitor.openSocket();
				OurJPEGHTTPServer httpServer= new OurJPEGHTTPServer(port + 67, serverMonitor);
				httpServer.start();
				
				AxisM3006V camera = new AxisM3006V();
				camera.init();

			    camera.connect();
			    
			    ImageGetterThread imageGetter = new ImageGetterThread(camera, serverMonitor);
			    
			    OutputStream os = clientSocket.getOutputStream();
				InputStream is = clientSocket.getInputStream();

				ServerInputThread inputThread = new ServerInputThread(serverMonitor, is);
				ServerOutputThread outputThread = new ServerOutputThread(serverMonitor, os);
				outputThread.start();
				inputThread.start();
				imageGetter.start();
				Logger.log("ServerMain", "Threads started");
				
				serverMonitor.waitForSocketClose();
				
				outputThread.interrupt();
				inputThread.interrupt();
				imageGetter.interrupt();
				camera.close();
				camera.destroy();
				clientSocket.close();
			}
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}

}
