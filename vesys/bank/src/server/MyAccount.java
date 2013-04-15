package server;

import bank.Account;
import bank.InactiveException;
import bank.OverdrawException;

public class MyAccount implements Account {

	private String number;
	private String owner;
	private double balance;
	private boolean active = true;

	public MyAccount(String owner, String number) {
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
