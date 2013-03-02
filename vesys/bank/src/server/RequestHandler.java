package server;

import java.io.*;
import java.net.Socket;

public class RequestHandler implements Runnable {
	
	private Socket socket;
	private InputStream in;
	private OutputStream out;
	String command;
	MyBank bank;
	
	public RequestHandler(Socket sock, MyBank bank) throws IOException {
		this.socket = sock;
		this.in = socket.getInputStream();
		this.out = socket.getOutputStream();
		this.bank = bank;
	}

	@Override
	public void run() {
		try {
			this.receiveResult();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		command = "";
		try {
			int c;
			while ((c = this.in.read())!= (int)';') {
				command += (char)c;	
				}
			System.out.println("Command received: " + command);
			this.executeCommand(command);
		}
		catch (Exception e) {
			System.err.println(e.toString());
		}*/
	}
	
	private void receiveResult() throws IOException {
		BufferedReader b = new BufferedReader(new InputStreamReader(in));
		String input = b.readLine();
		System.out.println("Server received: "+input);
		
	}
	
	public void sendCommand(String cmd, String params) throws IOException {
		PrintWriter outP = new PrintWriter(out);
		String msg = cmd +":"+params+";";
		outP.println(msg);
		outP.flush();
	}

	
	private void executeCommand(String cmd) throws IOException {
		switch (this.parseCommand(cmd)) {
			case "createAccount": 
				System.out.println("create Account "+this.parseParams(cmd)[0]);
				String number = this.bank.createAccount(this.parseParams(cmd)[0]);
				this.sendCommand(number,"");
				break;
			default: 
				break;
		}
	}
	private String parseCommand(String cmd) {
		return cmd.substring(0, cmd.indexOf(":"));
	}
	private String[] parseParams(String cmd) {
		String cmd2 = cmd.substring(cmd.indexOf(":")+1,cmd.length());
		return cmd2.split(",");
	}

}


