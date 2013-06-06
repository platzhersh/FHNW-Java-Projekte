package server.socket;

import java.io.*;
import java.net.Socket;

import bank.InactiveException;
import bank.OverdrawException;
import bank.driver.Command;
/***
 * Used to parse and process received commands
 * One RequestHandler per Socket
 * RequestHandler stops listening as soon as socket connection is closed
 * @author Christian Glatthard
 *
 */
public class RequestHandler implements Runnable {
	
	private Socket socket;
	String command;
	MyBank bank;
	boolean running;
	
	/**
	 * Constructor
	 * @param sock reference to client server socket connection
	 * @param bank reference to server bank object
	 * @throws IOException
	 */
	public RequestHandler(Socket sock, MyBank bank) throws IOException {
		this.socket = sock;
		this.bank = bank;
		this.running = true;
	}
	
	/***
	 * main function, waits for commands to process them
	 */
	@Override
	public void run() {
		while (this.running) {
			try {
				String cmd = Command.receive(this.socket);
				System.out.println("Server received: "+cmd);
				if (cmd != null) this.executeCommand(cmd);
				else Command.send("FAILURE","NullPointerException",cmd,this.socket);
			} 
			catch (IOException e) {
				e.printStackTrace();
				this.running = false;
			}

		}
	}
	
	/***
	 * processes the commands and sends response back (FAILURE / SUCCESS)
	 * @param cmd command string
	 * @throws IOException
	 */
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
			case "transfer":
				try {
					this.bank.transfer(this.bank.getAccount(params[0]), this.bank.getAccount(params[1]), Double.parseDouble(params[2]));
					Command.send("SUCCESS","",cmd,this.socket);
				}
				catch (IllegalArgumentException e) {
					Command.send("FAILURE","IllegalArgumentException",cmd,this.socket);
				} catch (OverdrawException e) {
					Command.send("FAILURE","OverdrawException",cmd,this.socket);
				} catch (InactiveException e) {
					Command.send("FAILURE","InactiveException",cmd,this.socket);
				}
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
				accounts =  (accounts.length() > 1) ?accounts.substring(1) : "";
				Command.send("SUCCESS", accounts, cmd, this.socket);
				break;
			case "getAccount":
				if (params[0].isEmpty()) Command.send("FAILURE", "",cmd, this.socket);
				else {
					String acc = this.bank.getAccount(params[0]).getNumber();
					Command.send("SUCCESS", acc, cmd, this.socket);
				}
				break;
			default: 
				Command.send("FAILURE", "unknown command", cmd,this.socket);
				break;
		}
	}
	

}


