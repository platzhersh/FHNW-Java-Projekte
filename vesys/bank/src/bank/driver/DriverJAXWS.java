package bank.driver;


// TODO: ALL


import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import jaxws.service.Webservices;

import bank.BankDriver;
import bank.InactiveException;
import bank.OverdrawException;

public class DriverJAXWS implements BankDriver {
	
	private JAXWSBank bank;
	private Webservices websvc;

	@Override
	public void connect(String[] args) throws IOException {
		URL url = new URL("http://localhost:9999/bank/jaxws?wsdl");
		
		QName qname = new QName("http://service.jaxws/","WebservicesImplService");
		
		Service service = Service.create(url, qname);
		
		websvc = service.getPort(Webservices.class);
		
		System.out.println("connected...");
		
		this.bank = new JAXWSBank(websvc);
		
	}

	@Override
	public void disconnect() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JAXWSBank getBank() {
		return this.bank;
	}
	

	static class JAXWSBank implements bank.Bank {

		private Webservices service;
		
		public JAXWSBank(Webservices websvc) throws IOException {
			this.service = websvc;
		}
		
		@Override
		public Set<String> getAccountNumbers() throws IOException {
			Set<String> activeAccounts = new HashSet<String>(Arrays.asList(service.getAccountNumbers()));
			return activeAccounts;
		}

		@Override
		public String createAccount(String owner) throws IOException {
			return service.createAccount(owner);
		}

		@Override
		public boolean closeAccount(String number) throws IOException {
			return service.closeAccount(number);
		}

		@Override
		public bank.Account getAccount(String number) throws IOException {
			return service.getAccount(number);
		}

		@Override
		public void transfer(bank.Account from, bank.Account to, double amount)
				throws IOException, InactiveException, OverdrawException, IllegalArgumentException {
			service.getAccount(to.getNumber()).deposit(amount);
			service.getAccount(from.getNumber()).withdraw(amount);
		}
	}
	
	static class JAXWSAccount implements bank.Account {
			private String number;
			Webservices service;

			JAXWSAccount(String number, Webservices svc) {
				this.number = number;
				this.service = svc;
				
			}

			@Override
			public double getBalance() throws IOException {
				return service.getBalance(this.number);
			}

			@Override
			public String getOwner() throws IOException {
				return service.getOwner(this.number);
			}

			@Override
			public String getNumber() {
				return this.number;
			}

			@Override
			public boolean isActive() throws IOException {
				return service.isActive(this.number);
			}

			@Override
			public void deposit(double amount) throws InactiveException, IOException, IllegalArgumentException {
				service.deposit(this.number, amount);
 			}

			@Override
			public void withdraw(double amount) throws InactiveException, OverdrawException, IOException, IllegalArgumentException {
				service.withdraw(this.number, amount);
			}
			

		}


}
