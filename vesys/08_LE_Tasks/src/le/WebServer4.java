package le;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class WebServer4 {
    public static void main(String[] args) throws IOException {
        Executor exec = Executors.newFixedThreadPool(10);
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true) {
        	System.out.println("Waiting for connection...");
            final Socket s = serverSocket.accept();
            System.out.println("Client connected: " + s.getInetAddress());
            Runnable task = new Runnable() {
                public void run() {
                    handleRequest(s);
                }
            };
            exec.execute(task);
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
