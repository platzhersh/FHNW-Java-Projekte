package le;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer3 {
    public static void main(String[] args) throws IOException {
        final ServerSocket serverSocket = new ServerSocket(8080);
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread() {
                public void run() {
                    while (true) {
                        try {
                        	System.out.println("Waiting for connection...");
                        	Socket s = serverSocket.accept();
                        	System.out.println("Client connected: " + s.getInetAddress());
                            handleRequest(s);
                        } catch (IOException e) { /* ... */ }
                    }
                }
            };
            t.start();
        }
    }

    static void handleRequest(Socket s) {
    	System.err.println("Handling request: " + Thread.currentThread().getName());
    	try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {}
    	System.err.println("Finished request processing.");
    }
}
