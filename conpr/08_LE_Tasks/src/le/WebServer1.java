package le;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer1 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true) {
        	System.out.println("Waiting for connection...");
            Socket s = serverSocket.accept();
            System.out.println("Client connected: " + s.getInetAddress());
            handleRequest(s);
        }
    }

    static void handleRequest(Socket s) {
    	System.err.println("Handling request: " + Thread.currentThread().getName());
		try {
			Thread.sleep(5_000);
			OutputStreamWriter os = new OutputStreamWriter(s.getOutputStream());
			os.write("HTTP/1.0 200 OK\r\nConnection: close\r\nServer: WebServer1 v0\r\nContent-Type: text/plain\r\n\r\n");
			os.write("Sequential and Slow");
			os.flush();
			s.close();
		} catch (Exception e) {}
    	System.err.println("Finished request processing.");
    }

}
