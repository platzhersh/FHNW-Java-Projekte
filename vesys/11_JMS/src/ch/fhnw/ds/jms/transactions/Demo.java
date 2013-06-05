package ch.fhnw.ds.jms.transactions;

import java.util.Hashtable;

import javax.jms.ConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Demo {
	
	private static ConnectionFactory factory;

	static {
		Hashtable<String, String> properties = new Hashtable<String, String>();
		properties.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.apache.activemq.jndi.ActiveMQInitialContextFactory");
		properties.put(Context.PROVIDER_URL, "tcp://localhost:61616");

		try {
			Context jndiContext = new InitialContext(properties);
			factory = (ConnectionFactory)jndiContext.lookup("ConnectionFactory");
		} catch (NamingException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static void main(String[] args) throws Exception {
		Retailer r = new Retailer("VendorOrderQueue", factory);
		Vendor v = new Vendor("VendorOrderQueue", "MonitorOrderQueue", "StorageOrderQueue", factory);
		Supplier s1 = new Supplier("HardDrive", "StorageOrderQueue", factory);
		Supplier s2 = new Supplier("Monitor", "MonitorOrderQueue", factory);
		
		new Thread(r, "Retailer").start();
		new Thread(v, "Vendor").start();
		new Thread(s1, "Supplier 1").start();
		new Thread(s2, "Supplier 2").start();
	}

}