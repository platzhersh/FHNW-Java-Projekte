package jaxws.service;

import java.util.Set;

import javax.jws.WebMethod;
import javax.jws.WebService;

import server.MyAccount;
import server.MyBank;
import server.Server;

import bank.Account;

@WebService(endpointInterface = "jaxws.service.Webservices")
public class WebservicesImpl implements Webservices{

	private MyBank bank;
	
	public WebservicesImpl(MyBank bnk) {
		this.bank = bnk;
	}
	
	@Override
	public String createAccount() {
		return "blubb";
	}

	@Override
	public boolean closeAccount() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@WebMethod
	public void deposit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void withdraw() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getOwner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getBalance() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MyAccount getAccount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] getAccountNumbers() {
		// TODO Auto-generated method stub
		return null;
	}

}
