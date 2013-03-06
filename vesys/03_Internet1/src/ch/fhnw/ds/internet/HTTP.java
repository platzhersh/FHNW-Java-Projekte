package ch.fhnw.ds.internet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Class HTTP connects to a host and prints the first 100 lines of the response.
 */
class HTTP {
	public static void main(String[] args) throws Exception {
		String host = "www.amazon.de";

		Socket s = new Socket(host, 80);
		PrintWriter out = new PrintWriter(s.getOutputStream());
	
		out.print("GET / HTTP/1.0\r\n\r\n");

//		out.print("GET / HTTP/1.1\r\nHost: "+host+"\r\n\r\n");
//		out.print("GET / HTTP/1.1\r\nAccept-Encoding: gzip\r\nHost: "+host+"\r\n\r\n");

//	    out.print("HEAD / HTTP/1.0\r\n\r\n");
//	    out.print("OPTIONS / HTTP/1.1\r\nHost: "+host+"\r\n\r\n");
//		out.print("DELETE /index.html HTTP/1.1\r\nHost: "+host+"\r\n\r\n");
		
		out.flush();
		
		BufferedReader r = new BufferedReader(new InputStreamReader(s.getInputStream()));
		String line = r.readLine();
		// print the first 100 lines
		int cnt = 0;
		while (line != null) {
			if(cnt++ < 100) System.out.println(line);
			line = r.readLine();
		}
		
		s.close();
	}
}
