package ch.fhnw.vesys;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;


public class Client {

	Socket s;
	int port;
	BufferedReader in;
	BufferedReader incons;
	PrintWriter out;
	
	public Client(int p) {
		port = p;
	}
	
	public void connect() {
		try {
			s = new Socket("localhost", port);
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			incons = new BufferedReader(new InputStreamReader(System.in));
			out = new PrintWriter(s.getOutputStream());
			
			String ln = new String();
			
			while  (!(ln.equals("---"))) {
				ln = in.readLine();
				System.out.println(ln);
			}
			
			System.out.println("now start typing");
			
			while (!(ln.equals("/quit"))) {
				out.write(incons.readLine()+"\n");
				out.flush();
				ln = in.readLine();
				System.out.println(ln);
			}
				
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			System.err.println("Socket closed. + s");
			s = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	public static void main(String[] args) {
		Client c = new Client(4201);
		c.connect();
		

	}

}
