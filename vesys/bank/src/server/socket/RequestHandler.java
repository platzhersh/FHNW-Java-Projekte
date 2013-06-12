package server.socket;

import helpers.Command;
import helpers.CommandHandler;

import java.io.*;
import java.net.Socket;

import server.MyBank;

import bank.InactiveException;
import bank.OverdrawException;
/***
 * Used to parse and process received commands
 * One RequestHandler per Socket
 * RequestHandler stops listening as soon as socket connection is closed
 * @author Christian Glatthard
 *
 */
public class RequestHandler implements Runnable {
	
	private Socket socket;
	private CommandHandler cmdHandle;
	String command;
	MyBank bank;
	boolean running;
	
	/**
	 * Constructor
	 * @param sock reference to client server socket connection
	 * @param bank reference to server bank object
	 * @throws IOException
	 */
	public RequestHandler(CommandHandler ch, MyBank bank) throws IOException {
		this.cmdHandle = ch;
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
				String cmd = cmdHandle.receive();
				System.out.println("Server received: "+cmd);
				if (cmd != null) this.executeCommand(cmd);
				else cmdHandle.send("FAILURE","NullPointerException",cmd);
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
	public void executeCommand(String cmd) throws IOException {
		String command = cmdHandle.parseCommand(cmd);
		String[] params = cmdHandle.parseParams(cmd);
		
		switch (command) {
			case "createAccount": 
				String number = this.bank.createAccount(params[0]);
				cmdHandle.send("SUCCESS",number,cmd);
				break;
			case "closeAccount":
				Boolean r = this.bank.closeAccount(params[0]);
				cmdHandle.send("SUCCESS", r.toString(),cmd);
				break;
			case "transfer":
				try {
					this.bank.transfer(this.bank.getAccount(params[0]), this.bank.getAccount(params[1]), Double.parseDouble(params[2]));
					cmdHandle.send("SUCCESS","",cmd);
				}
				catch (IllegalArgumentException e) {
					cmdHandle.send("FAILURE","IllegalArgumentException",cmd);
				} catch (OverdrawException e) {
					cmdHandle.send("FAILURE","OverdrawException",cmd);
				} catch (InactiveException e) {
					cmdHandle.send("FAILURE","InactiveException",cmd);
				}
				break;
			case "deposit":
				try {
					this.bank.getAccount(params[0]).deposit(Double.parseDouble(params[1]));
					cmdHandle.send("SUCCESS","",cmd);
				} 
				catch (NumberFormatException e) {
					cmdHandle.send("FAILURE","NumberFormatException",cmd);
				}
				catch (IllegalArgumentException e) {
					cmdHandle.send("FAILURE","IllegalArgumentException",cmd);
				}
				catch (InactiveException e ) {
					cmdHandle.send("FAILURE","InactiveException",cmd);
				}
				break;
			case "withdraw":
				try {
					this.bank.getAccount(params[0]).withdraw(Double.parseDouble(params[1]));
					cmdHandle.send("SUCCESS","",cmd);
				} 
				catch (OverdrawException e) {
					cmdHandle.send("FAILURE","OverdrawException",cmd);
				}
				catch (NumberFormatException e) {
					cmdHandle.send("FAILURE","NumberFormatException",cmd);
				}
				catch (IllegalArgumentException e) {
					cmdHandle.send("FAILURE","IllegalArgumentException",cmd);
				}
				catch (InactiveException e ) {
					cmdHandle.send("FAILURE","InactiveException",cmd);
				}
				break;
			case "getOwner":
				String owner = this.bank.getAccount(params[0]).getOwner();
				cmdHandle.send("SUCCESS", owner, cmd);
				break;
			case "getBalance":
				String bal = Double.toString(this.bank.getAccount(params[0]).getBalance());
				cmdHandle.send("SUCCESS", bal, cmd);
				break;
			case "isActive":
				String active = this.bank.getAccount(params[0]).isActive() ? "true" : "false";
				cmdHandle.send("SUCCESS", active, cmd);
				break;
			case "getAccountNumbers":
				String accounts = new String();
				for (String acc : this.bank.getAccountNumbers()) {
					accounts += ","+acc;
				}
				accounts =  (accounts.length() > 1) ?accounts.substring(1) : "";
				cmdHandle.send("SUCCESS", accounts, cmd);
				break;
			case "getAccount":
				if (params[0].isEmpty()) cmdHandle.send("FAILURE", "",cmd);
				else {
					String acc = this.bank.getAccount(params[0]).getNumber();
					cmdHandle.send("SUCCESS", acc, cmd);
				}
				break;
			default: 
				cmdHandle.send("FAILURE", "unknown command", cmd);
				break;
		}
	}
	

}


