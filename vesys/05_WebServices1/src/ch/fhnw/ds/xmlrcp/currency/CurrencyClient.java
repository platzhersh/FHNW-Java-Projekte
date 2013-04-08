package ch.fhnw.ds.xmlrcp.currency;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class CurrencyClient {
	public static void main (String [] args) throws Exception {
		XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
		config.setServerURL(new URL("http://localhost:8080/xmlrpc/"));
		
		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);
		
		List<Object> params = new ArrayList<Object>();
		params.add("100");
		params.add("EUR");
		params.add("CHF");
		
		Object result = client.execute("Currency.convert", params );
		System.out.println("The result is: "+result.toString());
	}
}
