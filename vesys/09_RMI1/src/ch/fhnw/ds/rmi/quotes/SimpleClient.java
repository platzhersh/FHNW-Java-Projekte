package ch.fhnw.ds.rmi.quotes;

import java.rmi.Naming;

public class SimpleClient  {
	
	public static void main(String[] args) throws Exception {
		//System.setSecurityManager(new RMISecurityManager());
		
		QuoteServer server;
		server = (QuoteServer)Naming.lookup("rmi://localhost/QuoteServer");

		server.addQuoteListener(
			new AbstractQuoteListener() {
				public void update(String s) {
					System.out.println(s);
				}
			}
		);
	}
}