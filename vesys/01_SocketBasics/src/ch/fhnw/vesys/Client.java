package ch.fhnw.vesys;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Client {
	
	static Socket s;
	static InputStream in;
	static String host = "localhost";
	
	public static void main(String args[]) {
		try {
			s = new Socket(host, 4200);
			in = s.getInputStream();
			int len;
			byte[] b = new byte[100];
			while ((len = in.read(b)) != -1) {
				System.out.write(b, 0, len);
			}
			in.close();
			s.close();
		}
		catch (IOException e) { 
			System.out.println(e.toString());
			System.exit(1);
			}
		finally {

		}
		
	}
	
	public Client(String host, int port) {
		try {
			s = new Socket(host, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
