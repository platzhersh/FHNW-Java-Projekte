package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class Server {
	

	public static void main(String args[]) {
		try {
			
			System.out.println("Warte auf Verbindung auf Port 4200");
			ServerSocket bankd = new ServerSocket(4200);
			Socket socket = bankd.accept();
			
			System.out.println("Verbindung hergestellt");
			
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();
			
			
			MyBank bank = new MyBank();
			
			System.out.println("Verbindung geschlossen");
			socket.close();
			bankd.close();
			
			
		}
		catch (IOException e) {
			System.err.println(e.toString());
			System.exit(1);
		}
	}
	
}
