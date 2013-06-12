package helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CommandHandlerImplSocket extends CommandHandler<Socket> {


	public CommandHandlerImplSocket(Socket sock) {
		super(sock);
	}

	@Override
	public void send(String cmd, String params) throws IOException {
		PrintWriter outP = new PrintWriter(s.getOutputStream());
		String msg = cmd +":"+params;
		outP.println(msg);
		outP.flush();
		
	}

	@Override
	public String receive() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		String input = br.readLine();
		return input;
	}

}
