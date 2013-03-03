package server;

import java.io.*;
import java.net.Socket;

import bank.InactiveException;
import bank.OverdrawException;
import bank.driver.Command;

public class RequestHandler implements Runnable {
	
	private Socket socket;
	private InputStream in;
	private OutputStream out;
	String command;
	MyBank bank;
	Boolean running;
	
	public RequestHandler(Socket sock, MyBank bank) throws IOException {
		this.socket = sock;
		this.in = socket.getInputStream();
		this.out = socket.getOutputStream();
		this.bank = bank;
		this.running = true;
	}

	@Override
	public void run() {
		while (this.running) {
			try {
				String cmd = Command.receive(this.socket);
				System.out.println("Server received: "+cmd);
				this.executeCommand(cmd);
			} 
			catch (IOException e) {
				e.printStackTrace();
				this.running = false;
			}

		}
	}
	
	
	private void executeCommand(String cmd) throws IOException {
		String command = Command.parseCommand(cmd);
		String[] params = Command.parseParams(cmd);
		
		switch (command) {
			case "createAccount": 
				String number = this.bank.createAccount(params[0]);
				Command.send("SUCCESS",number,cmd,this.socket);
				break;
			case "closeAccount":
				Boolean r = this.bank.closeAccount(params[0]);
				Command.send("SUCCESS", r.toString(),cmd, this.socket);
				break;
			case "deposit":
				try {
					this.bank.getAccount(params[0]).deposit(Double.parseDouble(params[1]));
					Command.send("SUCCESS","",cmd,this.socket);
				} 
				catch (NumberFormatException e) {
					Command.send("FAILURE","NumberFormatException",cmd,this.socket);
				}
				catch (IllegalArgumentException e) {
					Command.send("FAILURE","IllegalArgumentException",cmd,this.socket);
				}
				catch (InactiveException e ) {
					Command.send("FAILURE","InactiveException",cmd,this.socket);
				}
				break;
			case "withdraw":
				try {
					this.bank.getAccount(params[0]).withdraw(Double.parseDouble(params[1]));
					Command.send("SUCCESS","",cmd,this.socket);
				} 
				catch (OverdrawException e) {
					Command.send("FAILURE","OverdrawException",cmd,this.socket);
				}
				catch (NumberFormatException e) {
					Command.send("FAILURE","NumberFormatException",cmd,this.socket);
				}
				catch (IllegalArgumentException e) {
					Command.send("FAILURE","IllegalArgumentException",cmd,this.socket);
				}
				catch (InactiveException e ) {
					Command.send("FAILURE","InactiveException",cmd,this.socket);
				}
				break;
			case "getOwner":
				String owner = this.bank.getAccount(params[0]).getOwner();
				Command.send("SUCCESS", owner, cmd, this.socket);
				break;
			case "getBalance":
				String bal = Double.toString(this.bank.getAccount(params[0]).getBalance());
				Command.send("SUCCESS", bal, cmd, this.socket);
				break;
			case "isActive":
				String active = this.bank.getAccount(params[0]).isActive() ? "true" : "false";
				Command.send("SUCCESS", active, cmd, this.socket);
				break;
			case "getAccountNumbers":
				String accounts = new String();
				for (String acc : this.bank.getAccountNumbers()) {
					accounts += ","+acc;
				}			
				Command.send("SUCCESS", accounts, cmd, this.socket);
				break;
			default: 
				Command.send("FAILURE", "unknown command", cmd,this.socket);
				break;
		}
	}
	

}


