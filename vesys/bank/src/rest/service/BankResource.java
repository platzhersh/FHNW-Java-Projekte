package rest.service;

import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import server.MyAccount;
import server.MyBank;

import bank.InactiveException;

import com.sun.jersey.spi.resource.Singleton;

import com.google.gson.Gson;

@Singleton
@Path("/bank")
public class BankResource {
	private String msg = "Hello, world!";
	
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
	public String postPlain( @QueryParam("owner") String owner ) {
		return bank.createAccount(this.parseWhitespaces(owner));
	}
	
	// GET on /accounts/{id}
	// ===========

	@GET
	@Path("/accounts/{id}")
	@Produces("application/json")
	public String getAccPlain( @PathParam("id") String number ) {
		MyAccount acc = (MyAccount) bank.getAccount(number);
		return Double.toString(acc.getBalance());
	}

	// PUT on /accounts/{id}
	// ===========

	@GET
	@Path("/accounts/{id}")
	@Produces("application/json")
	public void putAccPlain( 
			@PathParam("id") String number, 
			@QueryParam("amount") double amount 
			) throws InactiveException {
		MyAccount acc = (MyAccount) bank.getAccount(number);
		acc.deposit(amount);
	}
	
}
