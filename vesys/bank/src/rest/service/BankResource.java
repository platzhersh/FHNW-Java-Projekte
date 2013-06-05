package rest.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Set;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;

import server.MyAccount;
import server.MyBank;

import bank.InactiveException;
import bank.OverdrawException;

import com.sun.jersey.spi.resource.Singleton;

import com.google.gson.Gson;

@Singleton
@Path("/bank")
public class BankResource {
	
	private MyBank bank;
	
	private Gson gson;

	public BankResource() {
		bank = new MyBank();
		gson = new Gson();
		System.out.println("BankResource() called");
	}
	
	private String parseWhitespaces(String s) {
		return s.replace("+", " ");
	}
	
	// GET on /accounts
	// ===========

	@GET
	@Path("/accounts")
	@Produces("application/json")
	public String getJson() {
		Set<String> accounts = bank.getAccountNumbers();
		String json = gson.toJson(accounts);
		return json + "\n";
	}
	
	// POST on /accounts
	// ===========

	@POST
	@Path("/accounts")
	@Produces("text/plain")
	public String postPlain( @QueryParam("owner") String owner ) throws UnsupportedEncodingException {
		owner = java.net.URLDecoder.decode(owner, "UTF-8");
		return bank.createAccount(this.parseWhitespaces(owner));
	}
	
	// GET on /accounts/{id}
	// ===========

	@GET
	@Path("/accounts/{id}")
	@Produces("application/json")
	public String getAccJson( @PathParam("id") String number ) {
		MyAccount acc = (MyAccount) bank.getAccount(number);
		return acc == null ? "null" : gson.toJson(acc);
	}

	// GET on /accounts/{id}/balance
	// ===========
	@GET
	@Path("/accounts/{id}/balance")
	@Produces("text/plain")
	public String getAccBalance( @PathParam("id") String number ) {
		MyAccount acc = (MyAccount) bank.getAccount(number);
		return Double.toString(acc.getBalance());
	}

	// GET on /accounts/{id}/owner
	// ===========
	@GET
	@Path("/accounts/{id}/owner")
	@Produces("text/plain")
	public String getAccOwner( @PathParam("id") String number ) {
		MyAccount acc = (MyAccount) bank.getAccount(number);
		return acc.getOwner();
	}
	
	// POST on /accounts/{id}/deposit/{amount}
	// ===========

	@POST
	@Path("/accounts/{id}/deposit/{amount}")
	@Produces("text/plain")
	public String postDeposit( 
			@PathParam("id") String number, 
			@PathParam("amount") double amount 
			) throws InactiveException {
		MyAccount acc = (MyAccount) bank.getAccount(number);
		try {
			acc.deposit(amount);
			return "OK";
		} catch (InactiveException e) {
			return "InactiveException";
		}
	}

	// POST on /accounts/{id}/withdraw/{amount}
	// ===========

	@POST
	@Path("/accounts/{id}/withdraw/{amount}")
	@Produces("text/plain")
	public String postWithdraw( 
			@PathParam("id") String number, 
			@PathParam("amount") double amount 
			) throws InactiveException, OverdrawException {
		MyAccount acc = (MyAccount) bank.getAccount(number);
		try {
			acc.withdraw(amount);
			return "OK";
		} catch (InactiveException e) {
			return "InactiveException";
		} catch (OverdrawException e) {
			return "OverdrawException";
		}
	}
	
	
	// DELETE on /accounts/{id}
	// ===========

	@DELETE
	@Path("/accounts/{id}")
	@Produces("application/json")
	public String delAcc( @PathParam("id") String number)  {
		return bank.closeAccount(number) ? "1" : "0";
	}
	
	// GET on /accounts/{id}/active
	// ===========

	@GET
	@Path("/accounts/{id}/active")
	@Produces("text/plain")
	public int headAcc( @PathParam("id") String number)  {
		MyAccount acc = (MyAccount) bank.getAccount(number);
		if (acc.isActive()) return 1;
		else if (acc == null) throw new WebApplicationException();
		else return 0;
	}
	
	// POST on /transfer/
	// ===========
	
	@POST
	@Path("/transfer")
	@Produces("text/plain")
	public String postTransfer( 
			@QueryParam("from") String from,
			@QueryParam("to") String to,
			@QueryParam("amount") double amount
			)  {
		try {
			this.bank.transfer(this.bank.getAccount(from), this.bank.getAccount(to), amount);
			return "OK";
		} catch (IOException e) {
			return "IOException";
		} catch (InactiveException e) {
			return "InactiveException";
		} catch (OverdrawException e) {
			return "OverdrawException";
		}
		
	}
	
}
