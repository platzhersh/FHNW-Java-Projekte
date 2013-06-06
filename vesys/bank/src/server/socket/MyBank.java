package server.socket;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import bank.Bank;
import bank.InactiveException;
import bank.OverdrawException;


public class MyBank implements Bank {

	private Map<String, MyAccount> accounts = new HashMap<String, MyAccount>();

	@Override
	public Set<String> getAccountNumbers() {
		
		Set<String> activeAccounts = new HashSet<String>();
		
		for (Entry<String, MyAccount> e : accounts.entrySet() ) {
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
	
		MyAccount acc = new MyAccount(owner, number);
		accounts.put(number, acc);
		return number;
	}

	@Override
	public boolean closeAccount(String number) {
		MyAccount acc = accounts.get(number);
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
		
		if (amount > from.getBalance()) throw new OverdrawException();
		else if (amount < 0) throw new IllegalArgumentException();
		else if (!from.isActive() || !to.isActive()) throw new InactiveException();
		else if (from == null || to == null) throw new IllegalArgumentException();
		else {		
			from.withdraw(amount);
			to.deposit(amount);
		}
		
	}
}
