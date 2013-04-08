package jaxws.service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import server.MyAccount;

@WebService
@SOAPBinding(style = Style.RPC)
public interface Webservices {

	@WebMethod String createAccount();
	@WebMethod boolean closeAccount();
	@WebMethod void deposit();
	@WebMethod void withdraw();
	@WebMethod String getOwner();
	@WebMethod double getBalance();
	@WebMethod boolean isActive();
	@WebMethod Object[] getAccountNumbers();
	@WebMethod MyAccount getAccount();

}
