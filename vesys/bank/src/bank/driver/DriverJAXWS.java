package bank.driver;


// TODO: ALL


import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import jaxws.service.Webservices;

import bank.BankDriver;
import bank.InactiveException;
import bank.OverdrawException;

public class DriverJAXWS implements BankDriver {
	
	private Bank bank;
	private Webservices websvc;

	@Override
	public void connect(String[] args) throws IOException {
		URL url = new URL("http://localhost:9999/bank/jaxws?wsdl");
		
		QName qname = new QName("http://service.jaxws/","WebservicesImplService");
		
		Service service = Service.create(url, qname);
		
		websvc = service.getPort(Webservices.class);
		
		System.out.println("connected...");
		
		this.bank = new Bank();
		
	}

	@Override
	public void disconnect() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Bank getBank() {
		return this.bank;
	}
	

	static class Bank implements bank.Bank {

		private Map<String, Account> accounts = new HashMap<String, Account>();

		@Override
		public Set<String> getAccountNumbers() {
			
			Set<String> activeAccounts = new HashSet<String>();
			
			for (Entry<String, Account> e : accounts.entrySet() ) {
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
		
			Account acc = new Account(owner, number);
			accounts.put(number, acc);
			return number;
		}

		@Override
		public boolean closeAccount(String number) {
			Account acc = accounts.get(number);
			if (acc.isActive() && acc.balance == 0) acc.deactivate();
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

	static class Account implements bank.Account {
		private String number;
		private String owner;
		private double balance;
		private boolean active = true;

		Account(String owner, String number) {
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
