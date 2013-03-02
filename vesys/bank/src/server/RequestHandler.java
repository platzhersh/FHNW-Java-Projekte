package server;

import java.io.*;
import java.net.Socket;

import bank.driver.Command;

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
			String cmd = Command.receive(this.socket);
			System.out.println("Server received: "+cmd);
			this.executeCommand(cmd);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void executeCommand(String cmd) throws IOException {
		switch (Command.parseCommand(cmd)) {
			case "createAccount": 
				System.out.println("create Account "+Command.parseParams(cmd)[0]);
				String number = this.bank.createAccount(Command.parseParams(cmd)[0]);
				Command.send("success",number,this.socket);
				break;
			default: 
				break;
		}
	}
	

}


