 package bank.driver;

import helpers.Command;
import helpers.CommandHandler;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import bank.Bank;
import bank.BankDriver;
import bank.InactiveException;
import bank.OverdrawException;

public class DriverWebSocket implements BankDriver {

	private SocketBank b;
	private Inet4Address remote;
	private Socket sock;
	private CommandHandler c;
	
	@Override
	public void connect(String args[]) throws IOException {
		if (args.length < 2) { throw new IOException("provide Host and Port"); }
		else {
			remote = (Inet4Address) Inet4Address.getByName(args[0]);
			sock = new Socket(remote, Integer.parseInt(args[1]));
			System.out.println("Connected to "+remote.getHostName()+":"+args[1]);
			b = new SocketBank(this);
		}
		
	}

	@Override
	public void disconnect() throws IOException {
			sock.close();	
			this.b = null;
	}

	@Override
	public Bank getBank() {
		return this.b;
	}
	

	static class SocketBank implements bank.Bank {

		private DriverWebSocket driver;
		String clientname = "tootallnate/websocket";

		
		public SocketBank(DriverWebSocket d) throws IOException {
			this.driver = d;
		}
		
		@Override
		public Set<String> getAccountNumbers() throws IOException {
			driver.c.send("getAccountNumbers", "");
			
			String cmd = Command.receive(driver.sock);
			Set<String> activeAccounts = new HashSet<String>();
			System.out.println("Client received: "+cmd);
			
			if (Command.parseCommand(cmd).equals("SUCCESS")) {
				String[] params = Command.parseParams(cmd);
				System.out.println("Accounts: "+params.length);
				for (String param : params) {
					if (!param.isEmpty() && !param.equals("") && param != null) {
						activeAccounts.add(param);
						}
					}
				}
			 
			return activeAccounts;
		}

		@Override
		public String createAccount(String owner) throws IOException {
			Command.send("createAccount", owner, driver.sock);
			String cmd = Command.receive(driver.sock);
			System.out.println("Client received: "+cmd);
			String number = Command.parseParams(cmd)[0];
			return number;
		}

		@Override
		public boolean closeAccount(String number) throws IOException {
			Command.send("closeAccount", number, driver.sock);
			String cmd = Command.receive(driver.sock);
			Boolean r = Command.parseParams(cmd)[0].equals("true");
			System.out.println("Client received: "+cmd);
			return r;
		}

		@Override
		public bank.Account getAccount(String number) throws IOException {
			
			bank.Account r;
			
			Command.send("getAccount", number, driver.sock);
			String cmd = Command.receive(driver.sock);
			System.out.println("Client received: "+cmd);
			String command = Command.parseCommand(cmd);
			
			if (command.equals("FAILURE")) r = null;
			else {
				r = new SocketAccount(number, driver);
			}
			
			return r;
		}

		@Override
		public void transfer(bank.Account from, bank.Account to, double amount)
				throws IOException, InactiveException, OverdrawException, IllegalArgumentException {
			Command.send("transfer", from.getNumber()+","+to.getNumber()+","+amount, driver.sock);
			String cmd = Command.receive(driver.sock);
			System.out.println("Client received: "+cmd);
			String command = Command.parseCommand(cmd);
			String[] params = Command.parseParams(cmd);
			
			if (command.equals("FAILURE")) {
				switch (params[0]) {
				case "InactiveException":
					throw new InactiveException();
				case "IllegalArgumentException":
					throw new IllegalArgumentException();
				case "OverdrawException":
					throw new OverdrawException();
				}
				
			}
			
		}
	}
	
	static class SocketAccount implements bank.Account {
			private String number;
			DriverWebSocket driver;

			SocketAccount(String number, DriverWebSocket s) {
				this.number = number;
				this.driver = s;
				
			}

			@Override
			public double getBalance() throws IOException {
				Command.send("getBalance", this.number, driver.sock);
				return Double.parseDouble(Command.parseParams(Command.receive(driver.sock))[0]);
			}

			@Override
			public String getOwner() throws IOException {
				Command.send("getOwner", this.number, driver.sock);
				return Command.parseParams(Command.receive(driver.sock))[0];
			}

			@Override
			public String getNumber() {
				return number;
			}

			@Override
			public boolean isActive() throws IOException {
				Command.send("isActive", this.number, driver.sock);
				return Command.parseParams(Command.receive(driver.sock))[0].equals("true");
			}

			@Override
			public void deposit(double amount) throws InactiveException, IOException, IllegalArgumentException {
				Command.send("deposit", this.number+","+Double.toString(amount), driver.sock);
				String cmd = Command.receive(driver.sock);
				System.out.println("Client received: "+cmd);
				String command = Command.parseCommand(cmd);
				String[] params = Command.parseParams(cmd);
				
				if (command.equals("FAILURE")) {
					switch (params[0]) {
					case "InactiveException":
						throw new InactiveException();
					case "IllegalArgumentException":
						throw new IllegalArgumentException();
					}
				}
 			}

			@Override
			public void withdraw(double amount) throws InactiveException, OverdrawException, IOException, IllegalArgumentException {
				Command.send("withdraw", this.number+","+Double.toString(amount), driver.sock);
				String cmd = Command.receive(driver.sock);
				System.out.println("Client received: "+cmd);
				String command = Command.parseCommand(cmd);
				String[] params = Command.parseParams(cmd);
				
				if (command.equals("FAILURE")) {
					switch (params[0]) {
					case "InactiveException":
						throw new InactiveException();
					case "IllegalArgumentException":
						throw new IllegalArgumentException();
					case "OverdrawException":
						throw new OverdrawException();
					}
				} 
			}
			

		}


	

	

}
