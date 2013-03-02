package bank.driver;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import bank.Bank;
import bank.BankDriver;
import bank.InactiveException;
import bank.OverdrawException;

public class DriverSocket implements BankDriver {

	private SocketBank b;
	private Inet4Address remote;
	private Socket sock;
	private ObjectOutputStream outObj;
	
	
	@Override
	public void connect(String[] args) throws IOException {
		if (args.length < 2) { throw new IOException("provide Host and Port"); }
		else {
			remote = (Inet4Address) Inet4Address.getByName(args[0]);
			sock = new Socket(remote, Integer.parseInt(args[1]));
			System.out.println("Connected to "+remote.getHostName()+":"+args[1]);
			b = new SocketBank();
		}
		
	}

	@Override
	public void disconnect() throws IOException {
			sock.close();	
	}

	@Override
	public Bank getBank() {
		return this.b;
	}
	
	public void sendCommand(Command cmd) throws IOException {
		this.outObj.writeObject(cmd);
	}
	
	static class SocketBank implements bank.Bank {

		private Map<String, SocketAccount> accounts = new HashMap<String, SocketAccount>();

		@Override
		public Set<String> getAccountNumbers() {
			
			Set<String> activeAccounts = new HashSet<String>();
			
			for (Entry<String, SocketAccount> e : accounts.entrySet() ) {
				if (e.getValue().isActive()) activeAccounts.add(e.getKey());
			}
			return activeAccounts;
		}

		@Override
		public String createAccount(String owner) {
			
			
			String number = "01-";
			Integer size = accounts.size();
			String number2 = size.toString();
			
			while (number.length()+number2.length() < 16) {
				number += "0";
			}
			number += size;
		
			SocketAccount acc = new SocketAccount(owner, number);
			accounts.put(number, acc);
			return number;
		}

		@Override
		public boolean closeAccount(String number) {
			SocketAccount acc = accounts.get(number);
			if (acc.isActive() && acc.getBalance() == 0) acc.deactivate();
			return !acc.isActive();
		}

		@Override
		public bank.Account getAccount(String number) {
			return (bank.Account) accounts.get(number);
		}

		@Override
		public void transfer(bank.Account from, bank.Account to, double amount)
				throws IOException, InactiveException, OverdrawException {
			from.withdraw(amount);
			to.deposit(amount);
			
		}
	}
	
	static class SocketAccount implements bank.Account {
			private String number;
			private String owner;
			private double balance;
			private boolean active = true;

			SocketAccount(String owner, String number) {
				this.owner = owner;
				this.number = number;
			}

			@Override
			public double getBalance() {
				return balance;
			}

			@Override
			public String getOwner() {
				return owner;
			}

			@Override
			public String getNumber() {
				return number;
			}

			@Override
			public boolean isActive() {
				return active;
			}

			@Override
			public void deposit(double amount) throws InactiveException {
				if (!this.isActive()) throw new InactiveException();
				else if (amount < 0) throw new IllegalArgumentException();
				else this.balance += amount;
			}

			@Override
			public void withdraw(double amount) throws InactiveException, OverdrawException {
				if (!this.isActive()) throw new InactiveException();
				else if (amount < 0) throw new IllegalArgumentException();
				else {
					if (this.getBalance() < amount) throw new OverdrawException();
					else this.balance -= amount;
				}
			}
			
			public void activate() {
				this.active = true;
			}

			public void deactivate() {
				this.active = false;
			}
		}



	

	

}
