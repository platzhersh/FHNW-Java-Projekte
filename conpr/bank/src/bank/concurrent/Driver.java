/*
 * Copyright (c) 2000-2014 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved. 
 */

package bank.concurrent;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import bank.Account;
import bank.Bank;
import bank.BankDriver;
import bank.InactiveException;
import bank.OverdrawException;

/*
* This class implements a concurrent driver which can be used to start and run concurrently
* 
* @see BankDriver
*/


public class Driver implements BankDriver {
	private Bank bank = null;

	@Override
	public void connect(String[] args){
		bank = new ConcurrentBank();
		System.out.println("connected...");
	}
	
	@Override
	public void disconnect(){
		bank = null;
		System.out.println("disconnected...");
	}
	
	@Override
	public bank.Bank getBank(){
		return bank;
	}

	static class ConcurrentBank implements Bank  {
		private final ConcurrentMap<String, Account> accounts = new ConcurrentHashMap<String, Account>();
		private final Object createLock = new Object();
		
		@Override
		public String createAccount(String owner) {
			// TODO: evt. schöneres Generieren der Account No.
			// Account No generieren bis hinzufügen muss atomar sein
			synchronized(createLock) {
				/*String number = "01-";
				Integer size = accounts.size();
				String number2 = size.toString();
				
				while (number.length()+number2.length() < 16) {
					number += "0";
				}
				number += size;*/
				
				String number = Integer.toString(accounts.size()+1);
			
				ConcurrentAccount acc = new ConcurrentAccount(owner, number);
				accounts.put(number, acc);
				return number;
			}
		}

		@Override
		public boolean closeAccount(String number) {
			ConcurrentAccount acc = (ConcurrentAccount) accounts.get(number);

			// ensure that balance is not changed during check
			synchronized(acc) {
				if (acc.isActive() && acc.getBalance() == 0) acc.deactivate();
			}
			
			// TODO: return false if already closed
			return !acc.isActive();
		}

		// not locked because of performance reasons
		@Override
		public Set<String> getAccountNumbers() throws IOException{
			Set<String> activeAccounts = new HashSet<String>();
			
			for (Entry<String, Account> e : accounts.entrySet() ) {
				if (e.getValue().isActive()) activeAccounts.add(e.getKey());
			}
			return activeAccounts;
		}
		
		@Override
		public Account  getAccount(String number){
			return accounts.get(number);
		}
		
		@Override
		public void transfer(Account from, Account to, double amount) throws IllegalArgumentException, IOException, OverdrawException, InactiveException {
			
			if (amount < 0) throw new IllegalArgumentException();
			
			// always take the account with the smaller number as outer lock to avoid deadlocks
			Account firstLock = from;
			Account secondLock = to;
			
			// TODO: lexographic compareTo instead if int parsing
			// from.getNumber().compareTo(to.getNumber()) < 0
			int fint = Integer.parseInt(from.getNumber());
			int tint = Integer.parseInt(to.getNumber());			
		
			if (tint < fint) {
				firstLock = to;
				secondLock = from;
			}
			
			synchronized(firstLock) {
				synchronized(secondLock) {

					if (!from.isActive() || !to.isActive()) throw new InactiveException();
					else if (null == from || null == to) throw new IllegalArgumentException();
					else {		
						from.withdraw(amount);
						to.deposit(amount);
					}
					
				}
			}

		}
	}

	static class ConcurrentAccount implements Account {
		private String owner = "Dagobert Duck";
		private String number = "DD-33-4499";
		// make volatile so the balance & active state always have the correct value
		private volatile double balance;
		private volatile boolean active;

		public ConcurrentAccount(String owner, String number) {
			this.owner = owner;
			this.number = number;
			this.active = true;
		}

		public void activate() {
			active = true;
		}
		
		public synchronized void deactivate() {
			active = false;
		}

		@Override
		public String getNumber() {
			return number;
		}

		@Override
		public String getOwner() {
			return owner;
		}
				
		@Override
		public boolean isActive() {
			return active;
		}

		// TODO: muss atomar ausgeführt werden
		@Override
		public synchronized void deposit(double amount) throws InactiveException {
			if (!this.isActive()) throw new InactiveException();
			else if (amount < 0) throw new IllegalArgumentException();
			else this.balance += amount;
		}

		// TODO: muss atomar ausgeführt werden
		@Override
		public synchronized void withdraw(double amount) throws InactiveException, OverdrawException {
			if (!this.isActive()) throw new InactiveException();
			else if (amount < 0) throw new IllegalArgumentException();
			else {
				if (this.getBalance() < amount) throw new OverdrawException();
				else this.balance -= amount;
			}
		}

		@Override
		public double getBalance() {
			return balance;
		}
	}

}
