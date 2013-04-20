package bank.driver;

import java.io.IOException;
import java.util.Set;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import bank.BankDriver;
import bank.InactiveException;
import bank.OverdrawException;


public class DriverREST implements BankDriver {

	Client client;
	String address;
	final Gson gson = new Gson();
	
	private RESTBank bank;

	@Override
	public void connect(String[] args) throws IOException {
		
		this.client = Client.create();
		this.address = "http://localhost:9998/bank/";
		
		this.bank = new RESTBank(client, address);
		System.out.println("DriverREST started...");
	}

	@Override
	public void disconnect() throws IOException {
		if (client != null)
			client.destroy();
			System.out.println("RestBankDriver DESTROYED...");
	}

	@Override
	public RESTBank getBank() {
		return this.bank;
	}
	

	static class RESTBank implements bank.Bank {

		private WebResource res;
		private Client client;
		private String address;
		final Gson gson = new Gson();
		
		public RESTBank(Client c, String address) throws IOException {
			this.client = c;
			this.address = address;
		}
		
		@Override
		public Set<String> getAccountNumbers() throws IOException {
			res = client.resource(address+"accounts");
			String response = res.get(String.class);
			System.out.println("getAccountNumbers: received "+response);
			Set<String> activeAccounts = gson.fromJson(response, Set.class);
			return activeAccounts;
		}

		@Override
		public String createAccount(String owner) throws IOException {
			owner = java.net.URLEncoder.encode(owner,"UTF-8");
			res = client.resource(address+"accounts?owner="+owner);
			String response = res.post(String.class);
			System.out.println("createAccount: received "+response);
			return response;
		}

		@Override
		public boolean closeAccount(String number) throws IOException {
			res = client.resource(address+"accounts/"+number);
			String response = res.delete(String.class);
			System.out.println("closeAccount: received "+response);
			return response.equals("1") ? true : false;
		}

		@Override
		public bank.Account getAccount(String number) throws IOException {
			number = number.isEmpty() ? "null" : number;
			res = client.resource(address+"accounts/"+number);
			String response = res.get(String.class);
			response = java.net.URLDecoder.decode(response, "UTF-8");
			System.out.println("getAccount: received "+response);
			
			bank.Account r;
			if (response.equals("null")) {
				System.err.println("  [Driver]: return NULL for getAccount("+number+")");
				r = null;
			}
			else r = new RESTAccount(number, client, address);
			return r;
		}

		@Override
		public void transfer(bank.Account from, bank.Account to, double amount)
				throws IOException, InactiveException, OverdrawException, IllegalArgumentException {
			res = client.resource(address+"transfer?"+from.getNumber()+"&to="+to.getNumber()+"&amount="+amount);
			String response = res.post(String.class);
			System.out.println("transfer: received "+response);
			switch (response) {
				case	"IOException":
					throw new IOException();
				case	"InactiveException":
					throw new InactiveException();
				case	"OverdrawException":
					throw new OverdrawException();
				case	"IllegalArgumentException":
					throw new IllegalArgumentException();
			}
		}
	}
	
	static class RESTAccount implements bank.Account {
			private String number;
			private WebResource res;
			private Client client;
			private String address;
			final Gson gson = new Gson();

			RESTAccount(String number, Client c, String address) {
				this.number = number;
				this.client = c;
				this.address = address;
				
			}

			@Override
			public double getBalance() throws IOException {
				res = client.resource(address+"accounts/"+this.number+"/balance");
				String response = res.get(String.class);
				System.out.println("getBalance: received "+response);
				double balance = Double.parseDouble(response);
				return balance;
			}

			@Override
			public String getOwner() throws IOException {			
				res = client.resource(address+"accounts/"+this.number+"/owner");
				String response = res.get(String.class);
				response = java.net.URLDecoder.decode(response, "UTF-8");
				System.out.println("getOwner: received "+response);
				return response;
			}

			@Override
			public String getNumber() {
				return this.number;
			}

			@Override
			public boolean isActive() throws IOException {
				res = client.resource(address+"accounts/"+this.number+"/active");
				String response = res.get(String.class);
				System.out.println("isActive: received "+response);
				boolean act = response.equals("1") ? true : false;
				return act;
			}

			@Override
			public void deposit(double amount) throws InactiveException, IOException, IllegalArgumentException {
				System.out.println("deposit "+amount);
				res = client.resource(address+"accounts/"+this.number+"/deposit/"+amount);
				String response = res.post(String.class);
				switch (response) {
				case	"IOException":
					throw new IOException();
				case	"InactiveException":
					throw new InactiveException();
				case	"IllegalArgumentException":
					throw new IllegalArgumentException();
			}
 			}

			@Override
			public void withdraw(double amount) throws InactiveException, OverdrawException, IOException, IllegalArgumentException {
				System.out.println("withdraw "+amount);
				res = client.resource(address+"accounts/"+this.number+"/withdraw/"+amount);
				String response = res.post(String.class);
				switch (response) {
				case	"IOException":
					throw new IOException();
				case	"InactiveException":
					throw new InactiveException();
				case	"OverdrawException":
					throw new OverdrawException();
				case	"IllegalArgumentException":
					throw new IllegalArgumentException();
			}
			}
			

		}


	

}
