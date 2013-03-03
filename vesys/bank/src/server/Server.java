package server;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;


/***
 * Class Client is used to manage the bank on the centralised server.
 * Per default the server runs on port 4200, but you can specify whatever port you want to run it.
 * 
 * 
 * @author Christian Glatthard
 *
 */
public class Server {
	boolean running = true;
	int port;
	ServerSocket bankd;
	Socket socket;
	MyBank bank;
	InputStream in;
	OutputStream out;
	RequestHandler r;
		
	public static void main(String args[]) throws IOException {
		int port = args[0].isEmpty() ? 4200 : Integer.valueOf(args[0]);
		Server server = new Server(port);
		server.run();
		server.stop();
	}

	public Server(int port) {
		try {
			
			System.out.println("Waiting for clients on "+Inet4Address.getLocalHost().getHostName()+":"+port);
			bankd = new ServerSocket(port);
		}
		catch (IOException e) {
			System.err.println(e.toString());
			System.exit(1);
		}
	}
	
	public void run() throws IOException {
		bank = new MyBank();
		
		while (this.running == true) {
			this.socket = bankd.accept();
			try {
				System.out.println("Connection established: "+this.socket.toString());		
				r = new RequestHandler(this.socket, this.bank);
				Thread t = new Thread(r);
				t.start();
				}
			catch (Exception e) {
				this.socket.close();
				System.err.println(e.toString());
				}
			}
		}
	
	public void stop() {
		try {
			socket.close();
			bankd.close(); 
			System.out.println("Connection closed");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}

