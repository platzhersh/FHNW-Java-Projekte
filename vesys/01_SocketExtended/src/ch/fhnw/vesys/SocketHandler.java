package ch.fhnw.vesys;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class SocketHandler implements Runnable {

	Socket s;
	
	public SocketHandler (Socket sock) {
		s = sock;
	}
	
	@Override
	public void run() {
		
		try {
			InputStream i = s.getInputStream();
			OutputStream o = s.getOutputStream();
			PrintWriter out = new PrintWriter(o);
			BufferedReader br = new BufferedReader(new InputStreamReader(i));
			
			out.write("Type whatever you like. To quit the connection enter \"/quit\".\n");
			out.write("---\n");
			
			out.flush();
			
			String ln = br.readLine();
			
			while (! (ln.equals("/quit"))) {
				System.out.println("Received "+ln+".");
				out.write(ln+"\n");
				out.flush();
				ln = br.readLine();
			}
			s.shutdownOutput();
			s.shutdownInput();
			s.close();
		} catch (SocketException e) {
			System.err.println("Socket closed. " + s);
			s = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	

}
