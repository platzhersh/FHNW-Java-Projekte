package server.jaxws;

import java.io.IOException;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import bank.IllegalBankArgumentException;
import bank.InactiveException;
import bank.OverdrawException;

@WebService
@SOAPBinding(style = Style.RPC)
public interface Webservices {

	String createAccount(String owner);
	boolean closeAccount(String number);
	void deposit(String number, double amount) throws IllegalBankArgumentException, IOException, InactiveException;
	void withdraw(String number, double amount) throws IllegalBankArgumentException, IOException, InactiveException, OverdrawException;
	void transfer(String from, String to, double amount) throws IllegalBankArgumentException, IOException, InactiveException, OverdrawException;
	String getOwner(String number) throws IOException;
	double getBalance(String number) throws IOException, IllegalBankArgumentException;
	boolean isActive(String number) throws IOException, IllegalBankArgumentException;
	String[] getAccountNumbers() throws IOException;
	String getAccount(String number);
	

}
