package jaxws.service;

import java.io.IOException;
import javax.jws.WebService;

import server.MyAccount;
import server.MyBank;
import bank.IllegalBankArgumentException;
import bank.InactiveException;
import bank.OverdrawException;

@WebService(endpointInterface = "jaxws.service.Webservices")
public class WebservicesImpl implements Webservices{

	private MyBank bank;
	
	public WebservicesImpl(MyBank bnk) {
		this.bank = bnk;
	}
	
	@Override
	public String createAccount(String owner) {
		System.out.println("Create account "+ owner);
		return this.bank.createAccount(owner);
	}

	@Override
	public boolean closeAccount(String number) {
		System.out.println("Close account "+ number);
		return this.bank.closeAccount(number);
	}

	@Override
	public void deposit(String number, double amount) throws IOException, InactiveException, IllegalBankArgumentException {
		System.out.println("Deposit "+amount+" to "+ number);
		try {
			this.bank.getAccount(number).deposit(amount);	
		} catch (IllegalArgumentException e) {
			throw new IllegalBankArgumentException();
		}

	}

	@Override
	public void withdraw(String number, double amount) throws IllegalBankArgumentException, IOException, InactiveException, OverdrawException {
		System.out.println("Withdraw "+amount+" from "+ number);
		try {
			this.bank.getAccount(number).withdraw(amount);
		} catch (IllegalArgumentException e) {
			throw new IllegalBankArgumentException();
		}
	}
	
	@Override
	public void transfer(String from, String to, double amount) throws IllegalBankArgumentException, InactiveException, IOException, OverdrawException {
		System.out.println("Transfer "+amount+" from "+ from + " to "+to);
		try {
			bank.transfer(bank.getAccount(from), bank.getAccount(to), amount);
		} catch (IllegalArgumentException e) {
			throw new IllegalBankArgumentException();
		}
			
	}

	@Override 
	public String getAccount(String number) {
		System.out.println("GetAccount "+number);
		if (bank.getAccount(number) != null) return number;
		else {
			System.err.println("  Account "+number+" does not exist!");
			return "";
		}
	}
	
	@Override
	public String getOwner(String number) throws IOException {
		System.out.println("Get owner "+ number);
		bank.Account acc = this.bank.getAccount(number);
		if (acc != null) return acc.getOwner();
		else return "";
	}

	@Override
	public double getBalance(String number) throws IOException, IllegalBankArgumentException {
		System.out.println("Get balance "+ number);
		if (this.bank.getAccount(number) == null) throw new IllegalBankArgumentException();
		return this.bank.getAccount(number).getBalance();
	}

	@Override
	public boolean isActive(String number) throws IOException, IllegalBankArgumentException {
		System.out.println("Get isActive "+ number);
		if (this.bank.getAccount(number) == null) throw new IllegalBankArgumentException();
		return this.bank.getAccount(number).isActive();
	}


	
	@Override
	public String[] getAccountNumbers() {
		System.out.println("Get account numbers");
		return this.bank.getAccountNumbers().toArray(new String[0]);
	}

}
