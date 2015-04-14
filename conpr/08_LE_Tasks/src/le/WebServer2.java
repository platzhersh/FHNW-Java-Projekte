package le;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer2 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true) {
        	System.out.println("Waiting for connection...");
            final Socket s = serverSocket.accept();
            System.out.println("Client connected: " + s.getInetAddress());
            Thread t = new Thread() {
                public void run() {
                    handleRequest(s);
                }
            };
            t.start();
        }
    }

    static void handleRequest(Socket s) {
    	System.err.println("Handling request: " + Thread.currentThread().getName());
    	try {
    		Thread.sleep(10_000);
			OutputStreamWriter os = new OutputStreamWriter(s.getOutputStream());
			os.write("HTTP/1.0 200 OK\r\nConnection: close\r\nServer: WebServer2 v0\r\nContent-Type: text/plain\r\n\r\n");
			os.write("Parallel and Fast until ...");
			os.flush();
			s.close();
		} catch (Exception e) {}
    	System.err.println("Finished request processing.");
    }
}
