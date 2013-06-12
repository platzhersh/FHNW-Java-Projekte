package server.socket;

import helpers.CommandHandler;
import helpers.CommandHandlerImplSocket;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

import server.MyBank;


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
	CommandHandler c;
		
	public static void main(String args[]) throws IOException {
		int port = args.length >= 1 ? Integer.valueOf(args[0]): 4200;
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
		
		while (this.running) {
			this.socket = bankd.accept();
			c = new CommandHandlerImplSocket(this.socket);
			try {
				System.out.println("Connection established: "+this.socket.toString());		
				r = new RequestHandler(c, this.bank);
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

