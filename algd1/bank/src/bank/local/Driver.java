package bank.local;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import bank.InactiveException;
import bank.OverdrawException;

public class Driver implements bank.BankDriver {
	private Bank bank = null;

	@Override
	public void connect(String[] args) {
		bank = new Bank();
		System.out.println("connected...");
	}

	@Override
	public void disconnect() {
		bank = null;
		System.out.println("disconnected...");
	}

	@Override
	public Bank getBank() {
		return bank;
	}

	static class Bank implements bank.Bank {

		private Map<String, Account> accounts = new HashMap<String, Account>();

		@Override
		public Set<String> getAccountNumbers() {
			System.out.println("Bank.getAccountNumbers has to be implemented");
			return new HashSet<String>(); // FIXME has to be replaced
		}

		@Override
		public String createAccount(String owner) {
			System.out.println("Bank.createAccount has to be implemented");
			return null;
		}

		@Override
		public boolean closeAccount(String number) {
			System.out.println("Bank.closeAccount has to be implemented");
			return false;
		}

		@Override
		public bank.Account getAccount(String number) {
			return (bank.Account) accounts.get(number);
		}

		@Override
		public void transfer(bank.Account from, bank.Account to, double amount)
				throws IOException, InactiveException, OverdrawException {
			System.out.println("Bank.transfer has to be implemented");
		}

	}

	static class Account implements bank.Account {
		private String number;
		private String owner;
		private double balance;
		private boolean active = true;

		Account(String owner) {
			this.owner = owner;
			// FIXME number has to be set
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
			System.out.println("Account.deposit has to be implemented");
		}

		@Override
		public void withdraw(double amount) throws InactiveException, OverdrawException {
			System.out.println("Account.withdraw has to be implemented");
		}

	}

}