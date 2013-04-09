package jaxws.service;

import java.io.IOException;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import bank.InactiveException;
import bank.OverdrawException;

import server.MyAccount;

@WebService
@SOAPBinding(style = Style.RPC)
public interface Webservices {

	@WebMethod String createAccount(String owner);
	@WebMethod boolean closeAccount(String number);
	@WebMethod void deposit(String number, double amount) throws IllegalArgumentException, IOException, InactiveException;
	@WebMethod void withdraw(String number, double amount) throws IllegalArgumentException, IOException, InactiveException, OverdrawException;
	@WebMethod String getOwner(String number) throws IOException;
	@WebMethod double getBalance(String number) throws IOException;
	@WebMethod boolean isActive(String number) throws IOException;
	@WebMethod String[] getAccountNumbers() throws IOException;
	// @WebMethod MyAccount getAccount(String number) throws IOException;

}
