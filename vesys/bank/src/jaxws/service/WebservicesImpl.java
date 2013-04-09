package jaxws.service;

import java.io.IOException;
import javax.jws.WebService;
import server.MyAccount;
import server.MyBank;
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
	public void deposit(String number, double amount) throws IllegalArgumentException, IOException, InactiveException {
		System.out.println("Deposit "+amount+" to "+ number);
		this.bank.getAccount(number).deposit(amount);

	}

	@Override
	public void withdraw(String number, double amount) throws IllegalArgumentException, IOException, InactiveException, OverdrawException {
		System.out.println("Withdraw "+amount+" from "+ number);
		this.bank.getAccount(number).withdraw(amount);
	}

	@Override
	public String getOwner(String number) throws IOException {
		System.out.println("Get owner "+ number);
		return this.bank.getAccount(number).getOwner();
	}

	@Override
	public double getBalance(String number) throws IOException {
		System.out.println("Get balance "+ number);
		return this.bank.getAccount(number).getBalance();
	}

	@Override
	public boolean isActive(String number) throws IOException {
		System.out.println("Get isActive "+ number);
		return this.bank.getAccount(number).isActive();
	}

	/*
	@Override
	public MyAccount getAccount(String number) throws IOException{
		return (MyAccount) this.bank.getAccount(number);
	}
	*/
	
	@Override
	public String[] getAccountNumbers() {
		System.out.println("Get account numbers");
		return this.bank.getAccountNumbers().toArray(new String[0]);
	}

}
