package server;

/*
 * Real-time and concurrent programming
 *
 * Minimalistic HTTP server solution.
 *
 * Package created by Patrik Persson, maintained by klas@cs.lth.se
 * Adapted for Axis cameras by Roger Henriksson 
 */

import util.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Itsy bitsy teeny weeny web server. Always returns an image, regardless
 * of the requested file name.
 */
public class OurJPEGHTTPServer extends Thread {

    // ----------------------------------------------------------- MAIN PROGRAM

    private static final byte[] CRLF = {13, 10};
    private static final String TAG = OurJPEGHTTPServer.class.getSimpleName();

    // ------------------------------------------------------------ CONSTRUCTOR
    private int myPort;                             // TCP port for HTTP server

    // --------------------------------------------------------- PUBLIC METHODS
    private ServerMonitor monitor;
    private ServerSocket serverSocket;

    /**
     * @param port The TCP port the server should listen to
     */
    public OurJPEGHTTPServer(int port, ServerMonitor monitor) {
        this.monitor = monitor;
        myPort = port;

    }


    // -------------------------------------------------------- PRIVATE METHODS

    /**
     * Read a line from InputStream 's', terminated by CRLF. The CRLF is
     * not included in the returned string.
     */
    private static String getLine(InputStream s)
            throws IOException {
        boolean done = false;
        String result = "";

        while (!done) {
            int ch = s.read();        // Read
            if (ch <= 0 || ch == 10) {
                // Something < 0 means end of data (closed socket)
                // ASCII 10 (line feed) means end of line
                done = true;
            } else if (ch >= ' ') {
                result += (char) ch;
            }
        }

        return result;
    }

    /**
     * Send a line on OutputStream 's', terminated by CRLF. The CRLF should not
     * be included in the string str.
     */
    private static void putLine(OutputStream s, String str)
            throws IOException {
        s.write(str.getBytes());
        s.write(CRLF);
    }

    // ----------------------------------------------------- PRIVATE ATTRIBUTES

    public void run() {
        try {
            handleRequests();
        } catch (IOException e) {
            Logger.error(TAG, "Error openening new ServerSocket");
            e.printStackTrace();
            System.exit(1);
        }
    }
    public void close(){
    	try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * This method handles client requests. Runs in an eternal loop that does
     * the following:
     * <UL>
     * <LI>Waits for a client to connect
     * <LI>Reads a request from that client
     * <LI>Sends a JPEG image from the camera (if it's a GET request)
     * <LI>Closes the socket, i.e. disconnects from the client.
     * </UL>
     * <p/>
     * Two simple help methods (getLine/putLine) are used to read/write
     * entire text lines from/to streams. Their implementations follow below.
     */
    public void handleRequests() throws IOException {
        serverSocket = new ServerSocket(myPort);
        Logger.info(TAG, "HTTP server operating at port " + myPort + ".");

        while (!isInterrupted()) {
            try {
                // The 'accept' method waits for a client to connect, then
                // returns a socket connected to that client.
                Socket clientSocket = serverSocket.accept();
                Logger.info(TAG, "Opened HTTP connection");

                // The socket is bi-directional. It has an input stream to read
                // from and an output stream to write to. The InputStream can
                // be read from using read(...) and the OutputStream can be
                // written to using write(...). However, we use our own
                // getLine/putLine methods below.
                InputStream is = clientSocket.getInputStream();
                OutputStream os = clientSocket.getOutputStream();

                // Read the request
                String request = getLine(is);

                // The request is followed by some additional header lines,
                // followed by a blank line. Those header lines are ignored.
                String header;
                boolean cont;
                do {
                    header = getLine(is);
                    cont = !(header.equals(""));
                } while (cont);

                Logger.info(TAG, "HTTP request '" + request
                        + "' received.");

                // Interpret the request. Complain about everything but GET.
                // Ignore the file name.
                if (request.substring(0, 4).equals("GET ")) {
                    // Got a GET request. Respond with a JPEG image from the
                    // camera. Tell the client not to cache the image
                    putLine(os, "HTTP/1.0 200 OK");
                    putLine(os, "Content-Type: image/jpeg");
                    putLine(os, "Pragma: no-cache");
                    putLine(os, "Cache-Control: no-cache");
                    putLine(os, "");                   // Means 'end of header'


                    ImageWrapper image = monitor.httpGetJPEG();
                    Logger.info("HttpServer", "Fetched image.");

                    os.write(image.image, 0, image.size);
                } else {
                    // Got some other request. Respond with an error message.
                    putLine(os, "HTTP/1.0 501 Method not implemented");
                    putLine(os, "Content-Type: text/plain");
                    putLine(os, "");
                    putLine(os, "No can do. Request '" + request
                            + "' not understood.");

                    System.out.println("Unsupported HTTP request!");
                }

                os.flush();                      // Flush any remaining content
                clientSocket.close();              // Disconnect from the client
                
            } catch (IOException e) {
                Logger.error(TAG, "Caught exception " + e);
            }
        }
        serverSocket.close();
    }

    // By convention, these bytes are always sent between lines
    // (CR = 13 = carriage return, LF = 10 = line feed)

}