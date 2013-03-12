package servlets;

import java.io.OutputStream;
import java.net.Socket;

public class Shutdown {
	
	private static int SHUTDOWN_PORT = 8005;
	
	public static void main(String[] args) throws Exception {
		Socket s = new Socket("localhost", SHUTDOWN_PORT);
//		Writer w = new OutputStreamWriter(s.getOutputStream());
//		w.write("SHUTDOWN\n");
//		w.flush();
//		w.close();
		OutputStream out = s.getOutputStream();
		String cmd = "SHUTDOWN\n";
		for(int i = 0; i < cmd.length(); i++)
			out.write(cmd.charAt(i));
		out.close();
		s.close();
	}
}
